/*
 * -----------------------------
 * Copyright (C) 2016, 上海宅米贸易有限公司, All rights reserved.
 * -----------------------------
 * File: PullToRefreshLayout.java
 * Author: LongLe
 *
 */

package com.songlin.dispatch.widget.pulltorefresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.songlin.dispatch.R;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 自定义的布局，用来管理三个子控件，其中一个是下拉头，一个是包含内容的pullableView（可以是实现Pullable接口的的任何View），
 * 还有一个上拉头，
 */
public class PullToRefreshLayout extends RelativeLayout {
    public static final String TAG = "PullToRefreshLayout";

    public static final String REFRESH = "refresh";
    public static final String LOAD = "loading";


    private String mStatus = REFRESH;
    // 初始状态
    public static final int INIT = 0;
    // 释放刷新
    public static final int RELEASE_TO_REFRESH = 1;
    // 正在刷新
    public static final int REFRESHING = 2;
    // 释放加载
    public static final int RELEASE_TO_LOAD = 3;
    // 正在加载
    public static final int LOADING = 4;
    // 操作完毕
    public static final int DONE = 5;
    // 当前状态
    private int mState = INIT;
    // 刷新回调接口
    private OnPullListener mListener;
    // 刷新成功
    public static final int SUCCEED = 0;
    // 刷新失败
    public static final int FAIL = 1;
    // 按下Y坐标，上一个事件点Y坐标
    private float mDownY, mLastY;

    // 下拉的距离。注意：pullDownY和pullUpY不可能同时不为0
    public float mPullDownY = 0;
    // 上拉的距离
    private float mPullUpY = 0;

    // 释放刷新的距离
    private float mRefreshDist = 200;
    // 释放加载的距离
    private float mLoadMoreDist = 200;

    private MyTimer mTimer;
    // 回滚速度
    public float mMoveSpeed = 8;
    // 第一次执行布局
    private boolean mIsLayout = false;
    // 在刷新过程中滑动操作
    private boolean mIsTouch = false;
    // 手指滑动距离与下拉头的滑动距离比，中间会随正切函数变化
    private float mRadio = 2;

    private AnimationDrawable mPullDownAnimationDrawable, mPullUpAnimationDrawable;

    // 默认下拉头
    private View mDefaultRefreshView;
    private ImageView mRefreshIv;
    // 默认上拉头
    private View mDefaultLoadMoreView;
    private ImageView mLoadingIv;
    // 实现了Pullable接口的View
    private View mPullableView;
    // 过滤多点触碰
    private int mEvents;
    // 这两个变量用来控制pull的方向，如果不加控制，当情况满足可上拉又可下拉时没法下拉
    private boolean mCanPullDown = true;
    private boolean mCanPullUp = true;

    private boolean mPullDownEnable = true;
    private boolean mPullUpEnable = true;

    // 执行自动回滚的handler
    private Handler mUpdateHandler;
    // 下拉刷新过程监听器
    private OnPullProcessListener mOnRefreshProcessListener;
    // 上拉加载更多过程监听器
    private OnPullProcessListener mOnLoadMoreProcessListener;

    // 下拉头
    private View mRefreshView;
    // 上拉头
    private View mLoadMoreView;

    public PullToRefreshLayout(Context context) {
        this(context, null, 0);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs, defStyle);
    }

    private void initView(Context context, AttributeSet attrs, int defStyle) {
        mUpdateHandler = new UpdateHandler(this);
        mTimer = new MyTimer(mUpdateHandler);
        // 添加上拉头和下拉头
        LayoutInflater inflater = LayoutInflater.from(context);
        mDefaultRefreshView = inflater.inflate(R.layout.recycler_view_refresh_head, this,
                false);
        mRefreshView = mDefaultRefreshView;
        mDefaultLoadMoreView = inflater.inflate(R.layout.recycler_view_load_more, this, false);
        mLoadMoreView = mDefaultLoadMoreView;
        addView(mDefaultRefreshView);
        addView(mDefaultLoadMoreView);
    }

    /**
     * 获取可拉取的视图
     *
     * @return
     */
    public View getPullableView() {
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (v instanceof Pullable) {
                mPullableView = v;
                return mPullableView;
            }
        }
        return mPullableView;
    }

    /**
     * 获取是下拉刷新还是上拉加载更多
     *
     * @return String "refresh" 或 "loading"
     */
    public String getStatus() {
        return mStatus;
    }

    private void hide() {
        mTimer.schedule(5);
    }

    /**
     * 完成刷新操作，显示刷新结果。注意：刷新完成后一定要调用这个方法
     *
     * @param refreshResult PullToRefreshLayout.SUCCEED代表成功，PullToRefreshLayout.FAIL代表失败
     */
    public void refreshFinish(int refreshResult) {
        if (null != mOnRefreshProcessListener) {
            mOnRefreshProcessListener.onFinish(mRefreshView,
                    OnPullProcessListener.REFRESH);
        }
        if (mPullDownAnimationDrawable != null) {
            mPullDownAnimationDrawable.stop();
        }
        switch (refreshResult) {
            case SUCCEED:
                // 刷新成功
                break;
            case FAIL:
            default:
                // 刷新失败
                break;
        }
        if (mPullDownY > 0) {
            // 刷新结果停留1秒
            new RemainHandler(this).sendEmptyMessageDelayed(0, 0);
        } else {
            changeState(DONE);
            hide();
        }
    }

    /**
     * 加载完毕，显示加载结果。注意：加载完成后一定要调用这个方法
     *
     * @param refreshResult PullToRefreshLayout.SUCCEED代表成功，PullToRefreshLayout.FAIL代表失败
     */
    public void loadMoreFinish(int refreshResult) {
        if (null != mOnLoadMoreProcessListener) {
            mOnLoadMoreProcessListener.onFinish(mLoadMoreView,
                    OnPullProcessListener.LOADMORE);
        }
        if (mPullDownAnimationDrawable != null) {
            mPullUpAnimationDrawable.stop();
        }
        switch (refreshResult) {
            case SUCCEED:
                // 加载成功
                break;
            case FAIL:
            default:
                // 加载失败
                break;
        }
        if (mPullUpY < 0) {
            // 刷新结果停留1秒
            new RemainHandler(this).sendEmptyMessageDelayed(0, 1000);
        } else {
            changeState(DONE);
            hide();
        }
    }

    /**
     * 改变状态
     *
     * @param to 状态码
     */
    private void changeState(int to) {
        mState = to;
        switch (mState) {
            case INIT:
                if (null != mOnRefreshProcessListener) {
                    mOnRefreshProcessListener.onPrepare(mRefreshView,
                            OnPullProcessListener.REFRESH);
                }
                if (null != mOnLoadMoreProcessListener) {
                    mOnLoadMoreProcessListener.onPrepare(mLoadMoreView,
                            OnPullProcessListener.LOADMORE);
                }
                // 下拉布局初始状态
                mRefreshIv.setVisibility(View.VISIBLE);
                // 上拉布局初始状态
                mLoadingIv.setVisibility(View.VISIBLE);
                break;
            case RELEASE_TO_REFRESH:
                if (null != mOnRefreshProcessListener) {
                    mOnRefreshProcessListener.onStart(mRefreshView,
                            OnPullProcessListener.REFRESH);
                }
                break;
            case REFRESHING:
                if (null != mOnRefreshProcessListener) {
                    mOnRefreshProcessListener.onHandling(mRefreshView,
                            OnPullProcessListener.REFRESH);
                }
                // 正在刷新状态
                mPullDownAnimationDrawable.start();
                break;
            case RELEASE_TO_LOAD:
                if (null != mOnLoadMoreProcessListener) {
                    mOnLoadMoreProcessListener.onStart(mLoadMoreView,
                            OnPullProcessListener.LOADMORE);
                }
                break;
            case LOADING:
                // 正在加载状态
                if (null != mOnLoadMoreProcessListener) {
                    mOnLoadMoreProcessListener.onHandling(mLoadMoreView,
                            OnPullProcessListener.LOADMORE);
                }
                mPullUpAnimationDrawable.start();
                break;
            case DONE:
                mRefreshView.setVisibility(View.VISIBLE);
                mLoadMoreView.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 不限制上拉或下拉
     */
    private void releasePull() {
        mCanPullDown = true;
        mCanPullUp = true;
    }

    /**
     * 设置是否可下拉刷新
     *
     * @param pullDownEnable
     */
    public void setPullDownEnable(boolean pullDownEnable) {
        mPullDownEnable = pullDownEnable;
    }

    /**
     * 设置是否可上拉刷新
     */
    public void setPullUpEnable(boolean pullUpEnable) {
        mPullUpEnable = pullUpEnable;
    }

    /*
     * （非 Javadoc）由父控件决定是否分发事件，防止事件冲突
     *
     * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "MotionEvent.ACTION_DOWN");
                mDownY = ev.getY();
                mLastY = mDownY;
                mTimer.cancel();
                mEvents = 0;
                releasePull();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                // 过滤多点触碰
                Log.i(TAG, "MotionEvent.ACTION_POINTER_UP");
                mEvents = -1;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "MotionEvent.ACTION_MOVE");
                Log.i(TAG, "Y:" + mDownY);
                if (null != mOnRefreshProcessListener) {
                    mOnRefreshProcessListener.onPull(mRefreshView, mPullDownY,
                            OnPullProcessListener.REFRESH);
                }
                if (null != mOnLoadMoreProcessListener) {
                    mOnLoadMoreProcessListener.onPull(mLoadMoreView, mPullUpY,
                            OnPullProcessListener.LOADMORE);
                }
                if (mEvents == 0) {
                    if (mPullDownY > 0
                            || (((Pullable) mPullableView).canPullDown()
                            && mCanPullDown && mPullDownEnable && mState != LOADING)) {
                        // 可以下拉，正在加载时不能下拉
                        // 对实际滑动距离做缩小，造成用力拉的感觉
                        mPullDownY = mPullDownY + (ev.getY() - mLastY) / mRadio;

                        if (mPullDownY < 0) {
                            mPullDownY = 0;
                            mCanPullDown = false;
                            mCanPullUp = true;
                        }
                        if (mPullDownY > getMeasuredHeight())
                            mPullDownY = getMeasuredHeight();
                        if (mState == REFRESHING) {
                            // 正在刷新的时候触摸移动
                            mIsTouch = true;
                        }
                    } else if (mPullUpY < 0
                            || (((Pullable) mPullableView).canPullUp() && mCanPullUp
                            && mPullUpEnable && mState != REFRESHING)) {
                        // 可以上拉，正在刷新时不能上拉
                        mPullUpY = mPullUpY + (ev.getY() - mLastY) / mRadio;
                        if (mPullUpY > 0) {
                            mPullUpY = 0;
                            mCanPullDown = true;
                            mCanPullUp = false;
                        }
                        if (mPullUpY < -getMeasuredHeight())
                            mPullUpY = -getMeasuredHeight();
                        if (mState == LOADING) {
                            // 正在加载的时候触摸移动
                            mIsTouch = true;
                        }
                    } else
                        releasePull();
                } else
                    mEvents = 0;
                mLastY = ev.getY();
                // 根据下拉距离改变比例
                mRadio = (float) (2 + 2 * Math.tan(Math.PI / 2 / getMeasuredHeight()
                        * (mPullDownY + Math.abs(mPullUpY))));
                if (mPullDownY > 0 || mPullUpY < 0)
                    requestLayout();
                if (mPullDownY > 0) {
                    if (mPullDownY <= mRefreshDist
                            && (mState == RELEASE_TO_REFRESH || mState == DONE)) {
                        // 如果下拉距离没达到刷新的距离且当前状态是释放刷新，改变状态为下拉刷新
                        changeState(INIT);
                    }
                    if (mPullDownY >= mRefreshDist && mState == INIT) {
                        // 如果下拉距离达到刷新的距离且当前状态是初始状态刷新，改变状态为释放刷新
                        changeState(RELEASE_TO_REFRESH);
                    }
                } else if (mPullUpY < 0) {
                    // 下面是判断上拉加载的，同上，注意pullUpY是负值
                    if (-mPullUpY <= mLoadMoreDist
                            && (mState == RELEASE_TO_LOAD || mState == DONE)) {
                        changeState(INIT);
                    }
                    // 上拉操作
                    if (-mPullUpY >= mLoadMoreDist && mState == INIT) {
                        changeState(RELEASE_TO_LOAD);
                    }

                }
                // 因为刷新和加载操作不能同时进行，所以pullDownY和pullUpY不会同时不为0，因此这里用(pullDownY +
                // Math.abs(mPullUpY))就可以不对当前状态作区分了
                if ((mPullDownY + Math.abs(mPullUpY)) > 8) {
                    // 防止下拉过程中误触发长按事件和点击事件
                    ev.setAction(MotionEvent.ACTION_CANCEL);
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "MotionEvent.ACTION_UP");
                if (mPullDownY > mRefreshDist || -mPullUpY > mLoadMoreDist)
                // 正在刷新时往下拉（正在加载时往上拉），释放后下拉头（上拉头）不隐藏
                {
                    mIsTouch = false;
                }
                if (mState == RELEASE_TO_REFRESH) {
                    changeState(REFRESHING);
                    // 刷新操作
                    mStatus = REFRESH;
                    if (mListener != null) {
                        mListener.onRefresh(this);
                    }
                } else if (mState == RELEASE_TO_LOAD) {
                    changeState(LOADING);
                    // 加载操作
                    mStatus = LOAD;
                    if (mListener != null) {
                        mListener.onLoadMore(this);
                    }
                }
                hide();
            default:
                break;
        }
        // 事件分发交给父类
        super.dispatchTouchEvent(ev);
        return true;
    }

    /**
     * @author chenjing 自动模拟手指滑动的task
     */
    private class AutoRefreshAndLoadTask extends
            AsyncTask<Integer, Float, String> {

        @Override
        protected String doInBackground(Integer... params) {
            while (mPullDownY < 4 / 3 * mRefreshDist) {
                mPullDownY += mMoveSpeed;
                publishProgress(mPullDownY);
                try {
                    Thread.sleep(params[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            changeState(REFRESHING);
            // 刷新操作
            if (mListener != null)
                mListener.onRefresh(PullToRefreshLayout.this);
            hide();
        }

        @Override
        protected void onProgressUpdate(Float... values) {
            if (mPullDownY > mRefreshDist)
                changeState(RELEASE_TO_REFRESH);
            requestLayout();
        }

    }

    /**
     * 自动刷新
     */
    public void autoRefresh() {
        mLoadMoreView.setVisibility(View.GONE);
        AutoRefreshAndLoadTask task = new AutoRefreshAndLoadTask();
        task.execute(20);
    }

    /**
     * 自动加载
     */
    public void autoLoad() {
        mRefreshView.setVisibility(View.GONE);
        mPullUpY = -mLoadMoreDist;
        requestLayout();
        changeState(LOADING);
        // 加载操作
        if (mListener != null)
            mListener.onLoadMore(this);
    }

    private void initView() {
        // 初始化下拉布局
        mRefreshIv = (ImageView) mDefaultRefreshView.findViewById(R.id.refresh_iv);
        mPullDownAnimationDrawable = (AnimationDrawable) mRefreshIv.getDrawable();
        // 初始化上拉布局
        mLoadingIv = (ImageView) mDefaultLoadMoreView.findViewById(R.id.loading_iv);
        mPullUpAnimationDrawable = (AnimationDrawable) mLoadingIv.getDrawable();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!mIsLayout) {
            // 这里是第一次进来的时候做一些初始化
            getPullableView();
            mIsLayout = true;
            initView();
            mRefreshView.measure(0, 0);
            mRefreshDist = mRefreshView.getMeasuredHeight();
            mLoadMoreView.measure(0, 0);
            mLoadMoreDist = mLoadMoreView.getMeasuredHeight();
        }
        // 改变子控件的布局，这里直接用(pullDownY + mPullUpY)作为偏移量，这样就可以不对当前状态作区分
        mRefreshView.layout(0,
                (int) (mPullDownY + mPullUpY) - mRefreshView.getMeasuredHeight(),
                mRefreshView.getMeasuredWidth(), (int) (mPullDownY + mPullUpY));
        mPullableView.layout(0, (int) (mPullDownY + mPullUpY),
                mPullableView.getMeasuredWidth(), (int) (mPullDownY + mPullUpY)
                        + mPullableView.getMeasuredHeight());
        mLoadMoreView.layout(0,
                (int) (mPullDownY + mPullUpY) + mPullableView.getMeasuredHeight(),
                mLoadMoreView.getMeasuredWidth(),
                (int) (mPullDownY + mPullUpY) + mPullableView.getMeasuredHeight()
                        + mLoadMoreView.getMeasuredHeight());
    }

    class MyTimer {
        private Handler handler;
        private Timer timer;
        private MyTask mTask;

        public MyTimer(Handler handler) {
            this.handler = handler;
            timer = new Timer();
        }

        public void schedule(long period) {
            if (mTask != null) {
                mTask.cancel();
                mTask = null;
            }
            mTask = new MyTask(handler);
            timer.schedule(mTask, 0, period);
        }

        public void cancel() {
            if (mTask != null) {
                mTask.cancel();
                mTask = null;
            }
        }

        class MyTask extends TimerTask {
            private Handler handler;

            public MyTask(Handler handler) {
                this.handler = handler;
            }

            @Override
            public void run() {
                handler.obtainMessage().sendToTarget();
            }

        }
    }

    /**
     * 刷新结果停留的handler
     */
    static class RemainHandler extends Handler {
        private WeakReference<PullToRefreshLayout> mLayout;

        public RemainHandler(PullToRefreshLayout layout) {
            mLayout = new WeakReference<PullToRefreshLayout>(layout);
        }

        @Override
        public void handleMessage(Message msg) {
            PullToRefreshLayout layout = mLayout.get();
            if (null != layout) {
                layout.changeState(DONE);
                layout.hide();
            }
        }
    }

    /**
     * 执行自动回滚的handler
     */
    static class UpdateHandler extends Handler {
        private WeakReference<PullToRefreshLayout> mLayout;

        public UpdateHandler(PullToRefreshLayout layout) {
            mLayout = new WeakReference<PullToRefreshLayout>(layout);
        }

        @Override
        public void handleMessage(Message msg) {
            PullToRefreshLayout layout = mLayout.get();
            if (null != layout) {
                // 回弹速度随下拉距离moveDeltaY增大而增大
                layout.mMoveSpeed = (float) (8 + 5 * Math.tan(Math.PI / 2
                        / layout.getMeasuredHeight()
                        * (layout.mPullDownY + Math.abs(layout.mPullUpY))));
                if (!layout.mIsTouch) {
                    // 正在刷新，且没有往上推的话则悬停，显示"正在刷新..."
                    if (layout.mState == REFRESHING
                            && layout.mPullDownY <= layout.mRefreshDist) {
                        layout.mPullDownY = layout.mRefreshDist;
                        layout.mTimer.cancel();
                    } else if (layout.mState == LOADING
                            && -layout.mPullUpY <= layout.mLoadMoreDist) {
                        layout.mPullUpY = -layout.mLoadMoreDist;
                        layout.mTimer.cancel();
                    }

                }
                if (layout.mPullDownY > 0)
                    layout.mPullDownY -= layout.mMoveSpeed;
                else if (layout.mPullUpY < 0)
                    layout.mPullUpY += layout.mMoveSpeed;
                if (layout.mPullDownY < 0) {
                    // 已完成回弹
                    layout.mPullDownY = 0;
                    layout.mRefreshIv.clearAnimation();
                    // 隐藏下拉头时有可能还在刷新，只有当前状态不是正在刷新时才改变状态
                    if (layout.mState != REFRESHING && layout.mState != LOADING)
                        layout.changeState(INIT);
                    layout.mTimer.cancel();
                    layout.requestLayout();
                }
                if (layout.mPullUpY > 0) {
                    // 已完成回弹
                    layout.mPullUpY = 0;
                    layout.mLoadingIv.clearAnimation();
                    // 隐藏上拉头时有可能还在刷新，只有当前状态不是正在刷新时才改变状态
                    if (layout.mState != REFRESHING && layout.mState != LOADING)
                        layout.changeState(INIT);
                    layout.mTimer.cancel();
                    layout.requestLayout();
                }
                // 刷新布局,会自动调用onLayout
                layout.requestLayout();
                // 没有拖拉或者回弹完成
                if (layout.mPullDownY + Math.abs(layout.mPullUpY) == 0)
                    layout.mTimer.cancel();
            }
        }

    }

    ;

    public void setOnPullListener(OnPullListener listener) {
        mListener = listener;
    }

    /**
     * 设置下拉刷新过程监听器
     *
     * @param onPullProcessListener
     */
    public void setOnRefreshProcessListener(
            OnPullProcessListener onPullProcessListener) {
        mOnRefreshProcessListener = onPullProcessListener;
    }

    /**
     * 设置上拉加载更多过程监听器
     *
     * @param onPullProcessListener
     */
    public void setOnLoadmoreProcessListener(
            OnPullProcessListener onPullProcessListener) {
        mOnLoadMoreProcessListener = onPullProcessListener;
    }

    /**
     * 刷新加载回调接口
     */
    public static interface OnPullListener {
        /**
         * 刷新操作
         */
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout);

        /**
         * 加载操作
         */
        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout);
    }

    /**
     * 下拉刷新或上拉加载更多过程监听器
     */
    public static interface OnPullProcessListener {
        public static final int REFRESH = 1; // 刷新

        public static final int LOADMORE = 2; // 加载更多

        /**
         * 准备 （提示下拉刷新或上拉加载更多）
         *
         * @param v
         * @param which 刷新或加载更多
         */
        public void onPrepare(View v, int which);

        /**
         * 开始 （提示释放刷新或释放加载更多）
         *
         * @param v
         * @param which 刷新或加载更多
         */
        public void onStart(View v, int which);

        /**
         * 处理中
         *
         * @param v
         * @param which 刷新或加载更多
         */
        public void onHandling(View v, int which);

        /**
         * 完成
         *
         * @param v
         * @param which 刷新或加载更多
         */
        public void onFinish(View v, int which);

        /**
         * 用于获取拉取的距离
         *
         * @param v
         * @param pullDistance
         * @param which        刷新或加载更多
         */
        public void onPull(View v, float pullDistance, int which);
    }

}


package com.songlin.dispatch.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.songlin.dispatch.adapter.ChatAdapter;
import com.songlin.dispatch.model.chat.ChatDetail;
import com.songlin.dispatch.utils.StringUtils;
import com.songlin.dispatch.widget.pulltorefresh.PullableRecyclerView;
import com.songlin.dispatch.R;
import com.songlin.dispatch.widget.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class OrderFragment extends Fragment implements PullToRefreshLayout.OnPullListener {


    @Bind(R.id.empty_iv)
    ImageView mEmptyImageContentIv;
    
    @Bind(R.id.empty_content)
    TextView mEmptyTextContentTv;

    @Bind(R.id.empty_view)
    View emptyView;

    @Bind(R.id.chat_detail_refresh_layout)
    PullToRefreshLayout mPullToRefreshLayout;

    @Bind(R.id.chat_detail_input_ev)
    EditText mChatDetailEv;

    @OnClick(R.id.chat_detail_input_ev)
    void inputChatDetail() {

    }

    //实时监控进货量起始值的输入框内容
    private TextWatcher mInputChatDetailTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            //可以将输入的内容实时显示
        }

        @Override
        public void afterTextChanged(Editable s) {
            editStart = mChatDetailEv.getSelectionStart();
            editEnd = mChatDetailEv.getSelectionEnd();
            if (!StringUtils.isEmpty(temp.toString())) {
                if (temp.length() > 150) {
                    Toast.makeText(getActivity(), "只能输入有150位", Toast.LENGTH_SHORT).show();
                    s.delete(editStart - 1, editEnd);
                    int tempSelection = editStart;
                    mChatDetailEv.setText(s);
                    mChatDetailEv.setSelection(tempSelection);
                }
            }
        }
    };

    @Bind(R.id.chat_detail_send_tv)
    TextView mSendChatContentTv;

    @OnClick(R.id.chat_detail_send_tv)
    void sendChatContent() {

        mChatDetailEv.addTextChangedListener(mInputChatDetailTextWatcher);

        //留言api
        

    }

    private String TAG = "MusicFragment";
    private View contentView;
    private ChatAdapter chatAdapter;
    private List<ChatDetail> chatDetailList = new ArrayList<>();
    private List<ChatDetail> temp = new ArrayList<>();
    private RecyclerView mStoresRv;
    private boolean isLoadingData = false;
    private boolean isLoadComplete = false;
    private int page = 1;
    private int pages = 1;

    public OrderFragment() {
        // Required empty public constructor
    }

    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_order, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, contentView);
        return contentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initView();
        super.onActivityCreated(savedInstanceState);
    }

    private void initView() {
        if (mPullToRefreshLayout == null) {
            Log.i(TAG, "mPullToRefreshLayout null");
            return;
        }
        mPullToRefreshLayout.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                System.out.println("isLoadComplete:" + isLoadComplete);
                System.out.println("isLoadingData:" + isLoadingData);
                if (!isLoadComplete && !isLoadingData) {
                    page++;
                    loadData(page);
                    pullToRefreshLayout.loadMoreFinish(PullToRefreshLayout.SUCCEED);
                }
                System.out.println("onRefresh");
            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
//                System.out.println("isLoadComplete:" + isLoadComplete);
//                System.out.println("isLoadingData:" + isLoadingData);
//                if (!isLoadComplete && !isLoadingData) {
//                    page++;
////                    Tracker tracker = TrackerUtil.getTracker(MessageBoardActivity.this);
////                    tracker.track(Structured.builder()
////                            .category("page_slide")
////                            .action(PageStatistical.SELECT_STORE_FRAGMENT)
////                            .label(String.valueOf(page))
////                            .build());
//                    loadData(page);
//                } else {
//                    pullToRefreshLayout.loadMoreFinish(PullToRefreshLayout.SUCCEED);
//                }
                pullToRefreshLayout.loadMoreFinish(PullToRefreshLayout.SUCCEED);
                System.out.println("onLoadMore");
            }
        });

        emptyView.setVisibility(View.GONE);
        mEmptyImageContentIv = (ImageView) emptyView.findViewById(R.id.empty_iv);
        mEmptyTextContentTv = (TextView) emptyView.findViewById(R.id.empty_content);
        mEmptyImageContentIv.setImageResource(R.drawable.ic_launcher);
        mEmptyTextContentTv.setText("暂无数据");
        mEmptyTextContentTv.setTextColor(Color.BLACK);
//        mEmptyTextContentTv.getBackground().setAlpha(0);

        mStoresRv = (PullableRecyclerView) mPullToRefreshLayout.getPullableView();
        mStoresRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        ChatDetail chatDetail = new ChatDetail();
        chatDetail.setAuthor("luosonglin");
        chatDetail.setAvatar("http://thumbnail0.baidupcs.com/thumbnail/b5529a0eb353d4f8b7c0016c9c28ca71?fid=3574182173-250528-142977963&time=1464580800&rt=sh&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-tg9Uvbey46sK47oCQMbYajWsfvE%3D&expires=8h&chkv=0&chkbd=0&chkpc=&dp-logid=3490646790879016919&dp-callid=0&size=c710_u400&quality=100");
        chatDetail.setPicture("http://thumbnail0.baidupcs.com/thumbnail/b5529a0eb353d4f8b7c0016c9c28ca71?fid=3574182173-250528-142977963&time=1464580800&rt=sh&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-tg9Uvbey46sK47oCQMbYajWsfvE%3D&expires=8h&chkv=0&chkbd=0&chkpc=&dp-logid=3490646790879016919&dp-callid=0&size=c710_u400&quality=100");
        chatDetail.setConversationId("1");
        chatDetail.setCreatedAt(1231451);
        chatDetail.setCreatedStr("2016-5-31");
        chatDetail.setMessage("hahaluo");
        List<ChatDetail> temp = new ArrayList<>();
        temp.add(chatDetail);
        chatDetailList.addAll(temp);

        Log.i(TAG, chatDetailList.size()+"");
        for (ChatDetail i : chatDetailList) {
            Log.i(TAG, i.getMessage());
        }


        chatAdapter = new ChatAdapter(getActivity().getApplicationContext(), chatDetailList);
        mStoresRv.setAdapter(chatAdapter);
//        loadData(page);
    }

    private void loadData(int page) {
        if (page == 1) {
            chatDetailList.clear();

            chatAdapter.addChatDetail(chatDetailList);
        } else if (page <= pages) {
            temp.clear();
            temp = chatDetailList;
            chatDetailList.addAll(temp);
            chatAdapter.refreshChatDetail(chatDetailList);
        } else {
            Toast.makeText(getActivity(),"没有更多数据", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 刷新操作
     *
     * @param pullToRefreshLayout
     */
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

    }

    /**
     * 加载操作
     *
     * @param pullToRefreshLayout
     */
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

    }
}

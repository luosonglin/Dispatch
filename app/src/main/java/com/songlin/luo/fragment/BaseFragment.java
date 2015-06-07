package com.songlin.luo.fragment;

import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;

/**
 * Created by luosonglin on 15/6/6.
 */
public abstract class BaseFragment extends Fragment
{


    @Override
    public void onResume()
    {
        super.onResume();
        if (getView() == null)
            return;
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                return event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK && onBackPressed();
            }
        });
    }

    //返回 True 则关闭当前 Fragment， False 则无反应
    public abstract boolean onBackPressed();

}
package com.songlin.luo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.songlin.luo.activity.dispatch.R;
import com.songlin.luo.activity.dispatch.SettingActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by luosonglin on 15/6/6.
 */
public class UpdateVersionFragment extends BaseFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_update_version,container,false);

        return view;
    }

    private View view;
    private boolean judge = false;
    private void initView() {
        try{
            judge = true;
            if(judge){
                Toast.makeText(getActivity(), "当前版本已是最新版本...   \n升级版仍在开发中，请耐心等待...", Toast.LENGTH_SHORT).show();
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getActivity(), SettingActivity.class);
                        startActivity(intent);
                    }
                };
                timer.schedule(task, 2300 * 1);

            }else{
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(),"更新失败,请与作者联系",Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initView();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }


}

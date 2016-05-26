package com.songlin.dispatch.activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.songlin.dispatch.activity.activities.R;
import com.songlin.dispatch.utils.AccountUtils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


import java.util.Set;

import butterknife.ButterKnife;
import butterknife.Bind;

public class LaunchScreenActivity extends AppCompatActivity {

    private static final String FIRSTOPEN = "firstOpen";
    private static final String NOTLOGIN = "notLogin";
    private static final String TOKENINVALID = "tokenInvalid";
    private static final String SUCCESS = "success";
    private static final String NOTNIGHTCAT = "notNightCat";
    private static final String NETERROR = "netError";

    @Bind(R.id.launch_parent_layout)
    View launchParentLayout;
    @Bind(R.id.version)
    TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        new BackgroundTask().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        Intent intent;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                PackageInfo packageInfo = getApplicationContext().getPackageManager()
                        .getPackageInfo(getPackageName(), 0);
                version.setText(packageInfo.versionName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            intent = new Intent();
        }

        @Override
        protected String doInBackground(Void... params) {

            String Tip = "";
            SharedPreferences sharedPreferences = getSharedPreferences("firstOpen", MODE_PRIVATE);
            if (sharedPreferences.getBoolean("firstOpen", true)) {
                intent.setClass(LaunchScreenActivity.this, GuidePageActivity.class);
                Tip = FIRSTOPEN;
            } else if (AccountUtils.getAccessToken(LaunchScreenActivity.this) == null) {
                intent.setClass(LaunchScreenActivity.this, LoginActivity.class);
                Tip = NOTLOGIN;
            } else if (AccountUtils.getAccessToken(LaunchScreenActivity.this) != null) {
                intent.setClass(LaunchScreenActivity.this, MainActivity.class);
                Tip = SUCCESS;
            }

//            //获取店铺开关情况
//            final StoreAPI storeAPI = APIServiceGenerator.generate(StoreAPI.class, LaunchScreenActivity.this);
//            try {
//                StoreWrap storeWrap = storeAPI.getStoreInfo();
//                if (!storeWrap.isSuccess()) {
//                    if ("401".equals(storeWrap.getData().getCode())) {
//                        intent.setClass(LaunchScreenActivity.this, LoginActivity.class);
//                        return TOKENINVALID;
//                    }
//                    return storeWrap.getData().getError();
//                } else {
//                    StoreWrapUtils.set(LaunchScreenActivity.this, storeWrap);
//                    //夜猫店
//                    if (storeWrap.getData().getStoreType() == 3) {
//                        intent.setClass(LaunchScreenActivity.this, MiniSupplierActivity.class);
//                        return SUCCESS;
//                    } else {
//                        intent = null;
//                        return NOTNIGHTCAT;
//                    }
//                }
//
//            } catch (Exception e) {
//                intent = null;
//                return NETERROR;
//            }
            return Tip;
        }

        @Override
        protected void onPostExecute(String resultString) {
            Log.d("test",resultString);
            switch (resultString) {
                case FIRSTOPEN:
                    startActivity(intent);
                    LaunchScreenActivity.this.finish();
                    break;
                case NOTNIGHTCAT:
                    //不是夜猫店
                    break;
                case TOKENINVALID:
                    //Token过期
                    startActivity(intent);
                    LaunchScreenActivity.this.finish();
                    break;
                case SUCCESS:
                    //成功
                    startActivity(intent);
                    LaunchScreenActivity.this.finish();
                    break;
                case NETERROR:
                    //服务器活在维护，或者网络出错
                    break;
                case NOTLOGIN:
                    startActivity(intent);
                    LaunchScreenActivity.this.finish();
                    break;
                default:
                    //账号异常
                    break;
            }

        }
    }
}

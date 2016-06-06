package com.songlin.dispatch.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.snappydb.SnappydbException;
import com.songlin.dispatch.R;
import com.songlin.dispatch.utils.AccountUtils;
import com.songlin.dispatch.utils.DBUtils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


import butterknife.ButterKnife;
import butterknife.Bind;

public class LaunchScreenActivity extends AppCompatActivity {

    private static final String FIRSTOPEN = "firstOpen";
    private static final String NOTFIRSTOPEN = "notFirstOpen";

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
            try {
                if (!DBUtils.isSet(LaunchScreenActivity.this, "user_name")) {
                    intent.setClass(LaunchScreenActivity.this, GuidePageActivity.class);
                    Tip = FIRSTOPEN;
                } else {
                    intent.setClass(LaunchScreenActivity.this, MainActivity.class);
                    Tip = NOTFIRSTOPEN;
                }
            } catch (SnappydbException e) {
                e.printStackTrace();
            }

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
                case NOTFIRSTOPEN:
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

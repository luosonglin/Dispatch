package com.songlin.luo.activity.dispatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/**
 * Created by luosonglin on 15/6/6.
 */
public class ChangePasswordActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_change_password);

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ChangePasswordActivity.this,SettingActivity.class);
                startActivity(intent);
                ChangePasswordActivity.this.finish();
            }
        });
    }


}

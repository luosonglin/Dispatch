package com.songlin.dispatch.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.songlin.dispatch.R;
import com.songlin.dispatch.widget.GuideWelcomeActivity;
import com.stephentuso.welcome.WelcomeScreenHelper;
import com.stephentuso.welcome.ui.WelcomeActivity;

public class GuidePageActivity extends AppCompatActivity {

    WelcomeScreenHelper welcomeScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);

        welcomeScreen = new WelcomeScreenHelper(this, GuideWelcomeActivity.class);
        welcomeScreen.show(savedInstanceState);

        welcomeScreen.forceShow();

//        findViewById(R.id.button_show).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                welcomeScreen.forceShow();
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == WelcomeScreenHelper.DEFAULT_WELCOME_SCREEN_REQUEST) {
            String welcomeKey = data.getStringExtra(WelcomeActivity.WELCOME_SCREEN_KEY);

            if (resultCode == RESULT_OK) {
                startActivity(new Intent(this, MainActivity.class));
//                Toast.makeText(getApplicationContext(), GuideWelcomeActivity.welcomeKey() + "已完成", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "您已跳过派工简介，可在版本页重新观看" + GuideWelcomeActivity.welcomeKey(), Toast.LENGTH_SHORT).show();
            }

        }

    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        welcomeScreen.onSaveInstanceState(outState);
    }

}

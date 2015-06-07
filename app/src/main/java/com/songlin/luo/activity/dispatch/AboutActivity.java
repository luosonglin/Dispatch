package com.songlin.luo.activity.dispatch;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by luosonglin on 15/6/6.
 */
public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_about);
    }
}

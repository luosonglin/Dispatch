package com.songlin.dispatch.activitiy_delete;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.songlin.dispatch.activity.activities.R;


public class AddOrderActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_order);

    }
}

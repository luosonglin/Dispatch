package com.songlin.luo.activity.dispatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by luosonglin on 15/6/1.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent1 = new Intent(this,LoginActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent1);
                MainActivity.this.finish();
            }
        };
        timer.schedule(task, 1000 * 1);
    }
}

package com.songlin.luo.activity.dispatch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by luosonglin on 15/6/6.
 */
public class CleanActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_clean);

        initView();
    }

    public boolean judge = false;
    public void initView() {
        try{
            cleanApplicationData(this);
            judge = true;
            if(judge){
                Toast.makeText(CleanActivity.this, "正在清理，请稍等片刻...\n清理完成后将自动跳转...", Toast.LENGTH_SHORT).show();
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(CleanActivity.this,AboutActivity.class);
                        startActivity(intent);

                    }
                };
                timer.schedule(task, 2300 * 1);

            }else{
                /*getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null)
                        .replace(R.id.container,new KnowAboutZhaiMeFragment())
                        .commit();*/
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 清除本应用内部缓存(/data/data/com.songlin.luo/cache)
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 清除本应用SharedPreference(/data/data/com.songlin.luo/shared_prefs)
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * 清除/data/data/com.songlin.luo/files下的内容
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    /**
     * 清除本应用所有的数据
     */
    public static void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanSharedPreference(context);
        cleanFiles(context);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_clean, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

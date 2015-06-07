package com.songlin.luo.activity.dispatch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.songlin.luo.adapter.ImageAdapter;
import com.songlin.luo.ui.GalleryView;

public class OrderCSideActivity extends Activity {

    /*private TextView tvTitle;
    private GalleryView gallery;
    private ImageAdapter adapter;
    private LinearLayout orderLayout;
    private String[] title = new String[]{
            "凭证1", "凭证2", "凭证3", "凭证4", "凭证5"};*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cside);

        //initRes();
        //java.lang.OutOfMemoryError

    }
    /*private void initRes(){
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        gallery = (GalleryView) findViewById(R.id.mygallery);

        adapter = new ImageAdapter(this);
        adapter.createReflectedImages();
        gallery.setAdapter(adapter);

        gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvTitle.setText(adapter.titles[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        gallery.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(OrderCSideActivity.this, "img " + (position+1) + " selected", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}

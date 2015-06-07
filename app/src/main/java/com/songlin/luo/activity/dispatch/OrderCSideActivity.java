package com.songlin.luo.activity.dispatch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
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
    private ImageView imageView,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cside);
        initView();
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
    protected void initView(){

        imageView = (ImageView)findViewById(R.id.imageView);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView3 = (ImageView)findViewById(R.id.imageView3);
        imageView4 = (ImageView)findViewById(R.id.imageView4);
        imageView5 = (ImageView)findViewById(R.id.imageView5);
        imageView6 = (ImageView)findViewById(R.id.imageView6);
        imageView7 = (ImageView)findViewById(R.id.imageView7);
        imageView8 = (ImageView)findViewById(R.id.imageView8);

        imageView.setImageResource(R.drawable.ic_launcher);
        imageView2.setImageResource(R.drawable.ic_launcher);
    }
}

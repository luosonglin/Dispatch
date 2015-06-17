package com.songlin.luo.activity.dispatch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.songlin.luo.ui.PullScrollView;

public class PersonActivity extends Activity implements
        PullScrollView.OnTurnListener {
    private PullScrollView mScrollView;
    private ImageView mHeadImg;

    private TableLayout mMainLayout;

    private LinearLayout home_img_bn_Layout, style_img_bn_layout,
            cam_img_bn_layout, shopping_img_bn_layout, show_img_bn_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_person);

        Intent intent = getIntent();
        boolean clickble = intent.getBooleanExtra("clickble", true);

        home_img_bn_Layout = (LinearLayout) findViewById(R.id.bottom_home_layout_ly);
        home_img_bn_Layout.setOnClickListener(clickListener_home);
        home_img_bn_Layout.setSelected(clickble);

        style_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_style_layout_ly);
        style_img_bn_layout.setOnClickListener(clickListener_style);

        cam_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_cam_layout_ly);
        cam_img_bn_layout.setOnClickListener(clickListener_cam);

        shopping_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_shopping_layout_ly);
        shopping_img_bn_layout.setOnClickListener(clickListener_shopping);

        show_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_show_layout_ly);
        show_img_bn_layout.setOnClickListener(clickListener_show);

        initView();
        showTable();

        findViewById(R.id.attention_user).setOnClickListener(map);
    }

    private OnClickListener clickListener_home = new OnClickListener() {

        @Override
        public void onClick(View v) {
            home_img_bn_Layout.setSelected(true);
            style_img_bn_layout.setSelected(false);
            cam_img_bn_layout.setSelected(false);
            shopping_img_bn_layout.setSelected(false);
            show_img_bn_layout.setSelected(false);

            Intent intent = new Intent();
            intent.setClass(PersonActivity.this, MenuActivity.class);
            intent.putExtra("clickble", true);
            startActivity(intent);
            PersonActivity.this.finish();
        }
    };
    private OnClickListener clickListener_style = new OnClickListener() {

        @Override
        public void onClick(View v) {
            home_img_bn_Layout.setSelected(false);
            style_img_bn_layout.setSelected(true);
            cam_img_bn_layout.setSelected(false);
            shopping_img_bn_layout.setSelected(false);
            show_img_bn_layout.setSelected(false);
            toastInfo("订单页面");
            Intent intent = new Intent();
            intent.setClass(PersonActivity.this,OrderActivity.class);
            intent.putExtra("clickble", true);
            startActivity(intent);
            PersonActivity.this.finish();
        }
    };
    private OnClickListener clickListener_cam = new OnClickListener() {

        @Override
        public void onClick(View v) {
            home_img_bn_Layout.setSelected(false);
            style_img_bn_layout.setSelected(false);
            cam_img_bn_layout.setSelected(true);
            shopping_img_bn_layout.setSelected(false);
            show_img_bn_layout.setSelected(false);

            Intent intent = new Intent();
            intent.setAction("android.media.action.STILL_IMAGE_CAMERA");// ����ϵͳ���
            startActivity(intent);
        }
    };
    private OnClickListener clickListener_shopping = new OnClickListener() {

        @Override
        public void onClick(View v) {
            home_img_bn_Layout.setSelected(false);
            style_img_bn_layout.setSelected(false);
            cam_img_bn_layout.setSelected(false);
            shopping_img_bn_layout.setSelected(true);
            show_img_bn_layout.setSelected(false);

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setData(ContactsContract.Contacts.CONTENT_URI);
            startActivity(intent);
        }
    };

    private OnClickListener clickListener_show = new OnClickListener() {

        @Override
        public void onClick(View v) {
            home_img_bn_Layout.setSelected(false);
            style_img_bn_layout.setSelected(false);
            cam_img_bn_layout.setSelected(false);
            shopping_img_bn_layout.setSelected(false);
            show_img_bn_layout.setSelected(false);
        }
    };

    protected void initView() {
        mScrollView = (PullScrollView) findViewById(R.id.scroll_view);
        mHeadImg = (ImageView) findViewById(R.id.background_img);

        mMainLayout = (TableLayout) findViewById(R.id.table_layout);

        mScrollView.setHeader(mHeadImg);
        mScrollView.setOnTurnListener(this);
    }

    public void showTable() {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.leftMargin = 30;
        layoutParams.bottomMargin = 10;
        layoutParams.topMargin = 10;

        for (int i = 0; i < 30; i++) {
            TableRow tableRow = new TableRow(this);
            TextView textView = new TextView(this);
            textView.setText("移动派工订单" + i + "号文件");
            textView.setTextSize(20);
            textView.setPadding(15, 15, 15, 15);

            tableRow.addView(textView, layoutParams);
            if (i % 2 != 0) {
                tableRow.setBackgroundColor(Color.LTGRAY);
            } else {
                tableRow.setBackgroundColor(Color.WHITE);
            }

            final int n = i;
            tableRow.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PersonActivity.this,
                            "派工单 " + n + " 号", Toast.LENGTH_SHORT).show();
                }
            });

            mMainLayout.addView(tableRow);
        }
    }

    //定位
    private OnClickListener map = new OnClickListener() {
        @Override
        public void onClick(View v) {
           /* Intent i =new Intent();
            i.setClass(PersonActivity.this, map.class);
            startActivity(i);*/
            Intent i =new Intent();
            i.setClass(PersonActivity.this, AddOrderActivity.class);
            startActivity(i);
        }
    };
    @Override
    public void onTurn() {

    }
    private void toastInfo(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

}

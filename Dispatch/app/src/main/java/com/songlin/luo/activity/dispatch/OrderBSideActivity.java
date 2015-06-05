package com.songlin.luo.activity.dispatch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderBSideActivity extends Activity {

    private LinearLayout orderLayout,orderLayout2,orderLayout3,orderLayout4;
    private TextView textView;
    private String[] title = new String[]{
            "材料", "型号", "数量", "单价", "保修卡号","备注信息"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_bside);

        initView();
    }
    public void initView(){
        orderLayout = (LinearLayout)findViewById(R.id.order_layout);
        int ii=orderLayout.getChildCount();
        String a=Integer.toString(ii);
        Toast.makeText(OrderBSideActivity.this,a, Toast.LENGTH_LONG).show();
        for (int i = 0; i < orderLayout.getChildCount(); i++) {
            View view = orderLayout.getChildAt(i);
            TextView title1 = (TextView) view.findViewById(R.id.order_detail_left);
            title1.setText(title[i]);
            TextView content = (TextView) view.findViewById(R.id.order_detail_right);

            switch (i) {
                case 0:
                    content.setText("处理器");
                    break;
                case 1:
                    content.setText("Intel Core7 Luo P7500 @2.60GHz，\n" +
                            "40MB L2 Cache，2800MHz FSB");
                    break;
                case 2:
                    content.setText("1");
                    break;
                case 3:
                    content.setText("1,200.00");
                    break;
                case 4:
                    content.setText("12345");
                    break;
                case 5:
                    content.setText("使用正常");
                    break;
            }
        }

        orderLayout2 = (LinearLayout)findViewById(R.id.order_layout2);
        for (int i = 0; i < orderLayout2.getChildCount(); i++) {
            View view = orderLayout2.getChildAt(i);
            TextView title1 = (TextView) view.findViewById(R.id.order_detail_left);
            title1.setText(title[i]);
            TextView content = (TextView) view.findViewById(R.id.order_detail_right);

            switch (i) {
                case 0:
                    content.setText("主芯片组");
                    break;
                case 1:
                    content.setText("Intel GM965+ICH8M");
                    break;
                case 2:
                    content.setText("1");
                    break;
                case 3:
                    content.setText("1,100.00");
                    break;
                case 4:
                    content.setText("12345");
                    break;
                case 5:
                    content.setText("适配正常");
                    break;
            }
        }

        orderLayout3 = (LinearLayout)findViewById(R.id.order_layout3);
        for (int i = 0; i < orderLayout3.getChildCount(); i++) {
            View view = orderLayout3.getChildAt(i);
            TextView title1 = (TextView) view.findViewById(R.id.order_detail_left);
            title1.setText(title[i]);
            TextView content = (TextView) view.findViewById(R.id.order_detail_right);

            switch (i) {
                case 0:
                    content.setText("内存");
                    break;
                case 1:
                    content.setText("8GB DDR2内存，1667MHz");
                    break;
                case 2:
                    content.setText("1");
                    break;
                case 3:
                    content.setText("2,000.00");
                    break;
                case 4:
                    content.setText("12345");
                    break;
                case 5:
                    content.setText("适配正常");
                    break;
            }
        }

        orderLayout4 = (LinearLayout)findViewById(R.id.order_layout4);
        for (int i = 0; i < orderLayout4.getChildCount(); i++) {
            View view = orderLayout4.getChildAt(i);
            TextView title1 = (TextView) view.findViewById(R.id.order_detail_left);
            title1.setText(title[i]);
            TextView content = (TextView) view.findViewById(R.id.order_detail_right);

            switch (i) {
                case 0:
                    content.setText("显卡");
                    break;
                case 1:
                    content.setText("GMA X3100集成显卡");
                    break;
                case 2:
                    content.setText("1");
                    break;
                case 3:
                    content.setText("1,000.00");
                    break;
                case 4:
                    content.setText("12345");
                    break;
                case 5:
                    content.setText("很好");
                    break;
            }
        }

        textView = (TextView)findViewById(R.id.order_detail_name);
        textView.setText("耗材单编号：12345");

    }

}

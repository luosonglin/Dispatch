package com.songlin.luo.activity.dispatch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderASideActivity extends Activity {

    private LinearLayout orderLayout;
    private TextView textView;
    private String[] title = new String[]{
            "保修项目", "保修客户", "保修时间", "保修客户地址", "客户联系方式", "客户要求时间", "维修师傅", "维修工人数","维修金额", "支付方式","是否支付","收款人","备注信息"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_aside);

        initView();
    }
    public void initView(){
        orderLayout = (LinearLayout)findViewById(R.id.order_layout);
        for (int i = 0; i < orderLayout.getChildCount(); i++) {
            View view = orderLayout.getChildAt(i);
            TextView title1 = (TextView) view.findViewById(R.id.order_detail_left);
            title1.setText(title[i]);
            TextView content = (TextView) view.findViewById(R.id.order_detail_right);

            switch (i) {
                case 0:
                    content.setText("电脑主机飞起来了");
                    break;
                case 1:
                    content.setText("罗崧麟");
                    break;
                case 2:
                    content.setText("2015-6-1 20:00");
                    break;
                case 3:
                    content.setText("二工大39栋302");
                    break;
                case 4:
                    content.setText("18817800000");
                    break;
                case 5:
                    content.setText("2015-6-2");
                    break;
                case 6:
                    content.setText("贺世节、李静");
                    break;
                case 7:
                    content.setText("2");
                    break;
                case 8:
                    content.setText("¥ 100.00");
                    break;
                case 9:
                    content.setText("货到付款");
                    break;
                case 10:
                    content.setText("是");
                    break;
                case 11:
                    content.setText("罗崧麟");
                    break;
                case 12:
                    content.setText("完美");
                    break;
            }
        }

        textView = (TextView)findViewById(R.id.order_detail_name);
        textView.setText("订单编号：12345");

    }

}

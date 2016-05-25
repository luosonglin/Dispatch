package com.songlin.dispatch.activitiy_delete;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.songlin.dispatch.activity.activities.R;

public class OrderASideActivity extends Activity {

    private LinearLayout orderLayout;
    private TextView textView;
    private String[] title = new String[]{
            "保修项目", "保修客户", "保修时间", "保修客户地址", "客户联系方式", "客户要求时间", "维修师傅", "维修工人数","维修金额", "支付方式","是否支付","收款人","备注信息"
    };
    public static final int MSG_LOGIN_RESULT = 0;
    public String serverUrl = "http://172.17.212.2:8080/androidWeb/servlet/orderMessage";
    /*private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case MSG_LOGIN_RESULT:
                    JSONObject json = (JSONObject) msg.obj;
                    handleLoginResult(json);

                    break;
            }
        };
    };*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_aside);

        /**
         * 答辩需要
         */
        orderLayout = (LinearLayout)findViewById(R.id.order_layout);
        for (int i = 0; i < orderLayout.getChildCount(); i++) {
            View view = orderLayout.getChildAt(i);
            TextView title1 = (TextView) view.findViewById(R.id.order_detail_left);
            title1.setText(title[i]);
            TextView content = (TextView) view.findViewById(R.id.order_detail_right);

            switch (i) {
                /**
                 * 答辩需要
                 */
                case 0:
                    content.setText("电脑主机坏了");
                    break;
                case 1:
                    content.setText("罗崧麟");
                    break;
                case 2:
                    content.setText("2015.10.28");
                    break;
                case 3:
                    content.setText("上海市闵行区上海交通大学");
                    break;
                case 4:
                    content.setText("18817802295");
                    break;
                case 5:
                    content.setText("2015.11.02");
                    break;
                case 6:
                    content.setText("李静、贺世节");
                    break;
                case 7:
                    content.setText("2");
                    break;
                case 8:
                    content.setText("300");
                    break;
                case 9:
                    content.setText("支付宝");
                    break;
                case 10:
                    content.setText("是");
                    break;
                case 11:
                    content.setText("李静");
                    break;
                case 12:
                    content.setText("客户很满意");
                    break;
            }
        }
        textView = (TextView)findViewById(R.id.order_detail_name);
        textView.setText("订单编号：12345");

//        initView();
    }
    /*public void initView(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("luo", "start network!");
                HttpClient client = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(serverUrl);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                //params.add(new BasicNameValuePair("username", "luo"));
                //params.add(new BasicNameValuePair("password", "123"));
                params.add(new BasicNameValuePair("id", "12345"));

                HttpResponse httpResponse = null;
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    httpResponse = client.execute(httpPost);
                    if(httpResponse.getStatusLine().getStatusCode() == 200) {
                        Log.d("luo", "network OK!");
                        HttpEntity entity = httpResponse.getEntity();
                        String entityString = EntityUtils.toString(entity);
                        String jsonString = entityString.substring(entityString.indexOf("{"));
                        Log.d("luo", "entity = " + jsonString);
                        JSONObject json = new JSONObject(jsonString);
                        sendMessage(MSG_LOGIN_RESULT, json);
                        Log.d("luo", "json = " + json);
                    }
                } catch (UnsupportedEncodingException e) {
                    Log.d("luo", e.toString());
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    Log.d("luo", e.toString());
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d("luo", e.toString());
                    e.printStackTrace();
                } catch (JSONException e) {
                    Log.d("luo", e.toString());
                    e.printStackTrace();
                }
            }
        }).start();
    }*/
    /*private void handleLoginResult(JSONObject json){
		*//*
		 * login_result:
		 * -1：登陆失败，未知错误！
		 * 0: 登陆成功！
		 * 1：登陆失败，用户名或密码错误！
		 * 2：登陆失败，用户名不存在！
		 * *//*
        int resultCode = -1;
        try {
            resultCode = json.getInt("result_code");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        switch(resultCode) {
            case 0:
                onLoginSuccess(json);
                break;
            case 1:
                Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(this, "用户名不存在", Toast.LENGTH_LONG).show();
                break;
            case -1:
            default:
                Toast.makeText(this, "登录失败！未知错误！", Toast.LENGTH_LONG).show();
                break;
        }
    }*/

    /*private void onLoginSuccess(JSONObject json) {

        try {
            orderLayout = (LinearLayout)findViewById(R.id.order_layout);
            for (int i = 0; i < orderLayout.getChildCount(); i++) {
                View view = orderLayout.getChildAt(i);
                TextView title1 = (TextView) view.findViewById(R.id.order_detail_left);
                title1.setText(title[i]);
                TextView content = (TextView) view.findViewById(R.id.order_detail_right);

                switch (i) {
                    *//**
                     * 答辩需要
                     *//*
                    case 0:
                        content.setText(json.getString("project_name"));
                        break;
                    case 1:
                        content.setText(json.getString("customer_name"));
                        break;
                    case 2:
                        content.setText(json.getString("customer_time"));
                        break;
                    case 3:
                        content.setText(json.getString("customer_address"));
                        break;
                    case 4:
                        content.setText(json.getString("customer_phone"));
                        break;
                    case 5:
                        content.setText(json.getString("require_time"));
                        break;
                    case 6:
                        content.setText(json.getString("workers_name"));
                        break;
                    case 7:
                        content.setText(json.getString("workers_num"));
                        break;
                    case 8:
                        content.setText("¥ "+json.getString("price"));
                        break;
                    case 9:
                        content.setText(json.getString("pay_method"));
                        break;
                    case 10:
                        content.setText(json.getString("pay_status"));
                        break;
                    case 11:
                        content.setText(json.getString("payee"));
                        break;
                    case 12:
                        content.setText(json.getString("remarks"));
                        break;
                }
            }
            textView = (TextView)findViewById(R.id.order_detail_name);
            textView.setText("订单编号：12345");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
    /*private void sendMessage(int what, Object obj) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = obj;
        mHandler.sendMessage(msg);
    }*/

}

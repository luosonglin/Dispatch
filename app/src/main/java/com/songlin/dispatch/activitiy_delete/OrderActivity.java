package com.songlin.dispatch.activitiy_delete;

import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.songlin.dispatch.activity.activities.R;

/**
 * Created by luosonglin on 15/6/1.
 */
public class OrderActivity extends Activity {

    public static List<BasicNameValuePair> fatherList = new ArrayList<BasicNameValuePair>();
    public static List<List<BasicNameValuePair>> childList = new ArrayList<List<BasicNameValuePair>>();
    private ExpandableListView exList;
    private EditText txtFind = null;
    private ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_order);
        for (int i = 0; i < 20; i++) {
            fatherList.add(new BasicNameValuePair("派工订单" + i, "派工订单" + i));
            List<BasicNameValuePair> cList = new ArrayList<BasicNameValuePair>();
            for (int j = 0; j < 1; j++) {
                cList.add(new BasicNameValuePair("今天订单销量非常不错，有balabala..."+ i + j,"今天订单销量非常不错，有balabala..."+ i + j));
                //("派工订单" + i + "号: " + j + "文件", "子订单"+ i + "号: " + j + "文件"));

            }
            childList.add(cList);
        }

        exList = (ExpandableListView) findViewById(R.id.exList);
        exList.setAdapter(new ExAdapter());
        exList.setGroupIndicator(null);
        exList.setDivider(null);
        //设置item点击的监听器
        exList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                /*Toast.makeText(
                        OrderActivity.this,
                        "你点击了",
                        Toast.LENGTH_SHORT).show();*/
                Intent intent = new Intent();
                intent.setClass(OrderActivity.this, SpecificOrderActivity.class);
                startActivity(intent);

                return false;
            }
        });


        list = (ListView) findViewById(R.id.listfind);
        list.setVisibility(View.GONE);



        txtFind = (EditText) findViewById(R.id.txtfind);
        txtFind.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !s.toString().equals("")) {
                    list.setAdapter(new ListAdapter(OrderActivity.this, s
                            .toString()));
                    list.setVisibility(View.VISIBLE);
                    exList.setVisibility(View.GONE);

                } else {

                    list.setVisibility(View.GONE);
                    exList.setVisibility(View.VISIBLE);
                }
            }
        });
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    protected class ListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        private List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();

        public ListAdapter(Context context, String s) {
            mInflater = LayoutInflater.from(context);

            for (int i = 0; i < childList.size(); i++) {
                for (int j = 0; j < childList.get(i).size(); j++) {
                    String tmp = childList.get(i).get(j).getValue();
                    if (tmp.indexOf(s) >= 0) {
                        list.add(new BasicNameValuePair(childList.get(i).get(j)
                                .getName(), tmp));
                    }
                }
            }
        }

        public int getCount() {
            return list.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            convertView = mInflater.inflate(R.layout.order_menu_child, null);
            TextView title = (TextView) convertView.findViewById(R.id.child);
            title.setText(list.get(position).getValue());
            return convertView;
        }
    }

    protected class ExAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return fatherList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childList.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return fatherList.get(groupPosition).getValue();
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childList.get(groupPosition).get(childPosition).getValue();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.order_menu_group, null);
            }
            TextView t = (TextView) view.findViewById(R.id.group);
            t.setText(fatherList.get(groupPosition).getValue());

            ImageView gImg = (ImageView) view.findViewById(R.id.tubiao);
            if (isExpanded)
                gImg.setBackgroundResource(R.drawable.mm_submenu_down_normal);
            else
                gImg.setBackgroundResource(R.drawable.mm_submenu_normal);
            return view;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.order_menu_child, null);
            }
            TextView t = (TextView) view.findViewById(R.id.child);
            t.setText(childList.get(groupPosition).get(childPosition)
                    .getValue());
            return view;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public void orderh(View view) {
            toastInfo("点击");
            Intent intent = new Intent();
            intent.setClass(OrderActivity.this, SpecificOrderActivity.class);
            startActivity(intent);
        }

        private void toastInfo(String string) {
            Toast.makeText(OrderActivity.this, string, Toast.LENGTH_SHORT).show();
        }

    }
}
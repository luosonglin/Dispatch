package com.songlin.luo.activity.dispatch;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.songlin.luo.sqlite.DBHelper;
import com.songlin.luo.sqlite.DbNews.News;

public class NewListActivity extends ListActivity {


    private final String[] COLS = {News._ID, News.TITLE, News.BODY};
    private final int[] IDS = {R.id.text_id, R.id.text_title, R.id.text_body};
    private DBHelper helper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helper = new DBHelper(this);
        Cursor cursor = helper.queryAllForCursor();
        if(cursor == null){
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }else if(cursor.getCount() <= 0){
            Toast.makeText(this, "no data exists.", Toast.LENGTH_SHORT).show();
        }

        SimpleCursorAdapter scadaAdapter = new SimpleCursorAdapter(this, R.layout.activity_new_list, cursor, COLS, IDS);
        setListAdapter(scadaAdapter);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        helper.cleanup();;
        helper = null;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
//		TextView text = (TextView) findViewById(R.id.text_id);	//note: don't obtain this TextView with the method findViewById()
        TextView tView = (TextView) ((LinearLayout)v).getChildAt(0);
        long newId = Long.parseLong(tView.getText().toString());	//get the id of this record
        News news = helper.queryByID(newId);
        Intent i = new Intent(NewListActivity.this, NewsDetailActivity.class);
        i.putExtra("extra_news", news.toStrings());
        startActivity(i);
    }


}

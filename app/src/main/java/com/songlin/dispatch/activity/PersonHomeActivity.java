package com.songlin.dispatch.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.songlin.dispatch.R;
import com.songlin.dispatch.widget.ui.PullScrollView;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonHomeActivity extends AppCompatActivity implements PullScrollView.OnTurnListener {


    private RecyclerView recycleView;
    private PullScrollView mScrollView;
    private ImageView mHeadImg;
    private TableLayout mMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_home);

        mScrollView = (PullScrollView) findViewById(R.id.scroll_view);
        mHeadImg = (ImageView) findViewById(R.id.background_img);
        mMainLayout = (TableLayout) findViewById(R.id.table_layout);
        mScrollView.setHeader(mHeadImg);
        mScrollView.setOnTurnListener(this);
    }

    /**
     * 翻转回调方法
     */
    @Override
    public void onTurn() {

    }
}

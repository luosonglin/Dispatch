package com.songlin.dispatch.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.snappydb.SnappydbException;
import com.songlin.dispatch.R;
import com.songlin.dispatch.utils.DBUtils;


public class PersonFragment extends Fragment {

    private View contentView;
    private TextView nameTv;

    public PersonFragment() {
        // Required empty public constructor
    }

    public static PersonFragment newInstance() {
        return new PersonFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_person, container, false);
        nameTv = (TextView)contentView.findViewById(R.id.user_name_tv);
        try {
            nameTv.setText(DBUtils.get(getActivity(), "user_name"));
        } catch (SnappydbException e) {
            e.printStackTrace();
        }

        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initView();
        super.onActivityCreated(savedInstanceState);
    }

    private void initView() {
        contentView.findViewById(R.id.balance_wrap_rlyt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "haha", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

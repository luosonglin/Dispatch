package com.songlin.dispatch.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.songlin.dispatch.R;


public class IdiomFragment extends Fragment {


    public IdiomFragment() {
        // Required empty public constructor
    }

    public static IdiomFragment newInstance() {
        IdiomFragment fragment = new IdiomFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View contentView = inflater.inflate(R.layout.fragment_report, container, false);
        return contentView;
    }

}

package com.songlin.dispatch.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.songlin.dispatch.activity.activities.R;


public class MusicFragment extends Fragment {


    public MusicFragment() {
        // Required empty public constructor
    }

    public static MusicFragment newInstance() {
        MusicFragment fragment = new MusicFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_music, container, false);
        // Inflate the layout for this fragment
        return contentView;
    }
}

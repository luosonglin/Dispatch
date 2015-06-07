package com.songlin.luo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.songlin.luo.activity.dispatch.R;
import com.songlin.luo.activity.dispatch.SettingActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by luosonglin on 15/6/6.
 */
public class UpdateVersionFragment extends BaseFragment implements updateVersionFragment.OnFragmentInteractionListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateVersionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateVersionFragment newInstance(String param1, String param2) {
        UpdateVersionFragment fragment = new UpdateVersionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public UpdateVersionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initView();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_version, container, false);
    }
    private View view;
    private boolean judge = false;
    private void initView() {
        try{
            judge = true;
            if(judge){
                Toast.makeText(getActivity(), "当前版本已是最新版本...   \n升级版仍在开发中，请耐心等待...", Toast.LENGTH_SHORT).show();
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getActivity(), SettingActivity.class);
                        startActivity(intent);
                    }
                };
                timer.schedule(task, 2300 * 1);

            }else{
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(),"更新失败,请与作者联系",Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}

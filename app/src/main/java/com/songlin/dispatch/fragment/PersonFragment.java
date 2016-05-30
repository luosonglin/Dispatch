package com.songlin.dispatch.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.songlin.dispatch.R;
import com.songlin.dispatch.activity.PersonHomeActivity;
import com.songlin.dispatch.widget.ui.PullScrollView;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class PersonFragment extends Fragment {
    @Bind(R.id.user_logo_civ)
    CircleImageView mAvatarIv;
    @OnClick(R.id.user_logo_civ)
    void toPersonCenter() {
        startActivity(new Intent(getActivity(), PersonHomeActivity.class));
    }

    @Bind(R.id.balance_wrap_rlyt)
    RelativeLayout mBalanceRlyt;
    @OnClick(R.id.balance_wrap_rlyt)
    void to() {
        Toast.makeText(getActivity(), "haha", Toast.LENGTH_SHORT).show();
    }

    private View contentView;

    public PersonFragment() {
        // Required empty public constructor
    }

    public static PersonFragment newInstance() {
        PersonFragment fragment = new PersonFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentView = inflater.inflate(R.layout.fragment_person, container, false);

        return contentView;
    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


        super.onActivityCreated(savedInstanceState);
    }
}

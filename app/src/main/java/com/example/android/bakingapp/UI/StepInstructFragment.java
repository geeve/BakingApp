package com.example.android.bakingapp.UI;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepInstructFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepInstructFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_VIDEO_URL = "video_url";
    private static final String ARG_STEP_DESCRIPTION = "step_description";

    // TODO: Rename and change types of parameters
    private String mVideoUrl;
    private String mStepDescription;

    @BindView(R.id.step_video_exoplayer)
    SimpleExoPlayerView mSimpleExoPlayerView;
    @BindView(R.id.step_description)
    TextView mStepDescriptionView;
    private Unbinder unbinder;

    private static final String LOG_TAG = StepInstructFragment.class.getSimpleName();
    public StepInstructFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StepInstructFragment.
     */

    public static StepInstructFragment newInstance(String param1, String param2) {
        StepInstructFragment fragment = new StepInstructFragment();
        Bundle args = new Bundle();
        args.putString(ARG_VIDEO_URL, param1);
        args.putString(ARG_STEP_DESCRIPTION, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mVideoUrl = getArguments().getString(ARG_VIDEO_URL);
            mStepDescription = getArguments().getString(ARG_STEP_DESCRIPTION);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_instruct,container,false);
        unbinder = ButterKnife.bind(this,view);
        Log.v(LOG_TAG,mStepDescription);
        mStepDescriptionView.setText(mStepDescription);
        return inflater.inflate(R.layout.fragment_step_instruct, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

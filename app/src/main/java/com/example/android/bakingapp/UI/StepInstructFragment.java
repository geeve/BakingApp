package com.example.android.bakingapp.UI;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.StepDescriptionActivity;
import com.example.android.bakingapp.data.RecipeContract;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepInstructFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepInstructFragment extends Fragment {

    private static final String ARG_STEP_VIDEO_URL = "step_video_url";
    private static final String ARG_STEP_DESCRIPTION = "step_description";


    private String mStepVideoUrl;
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
     * @param param1 ARG_STEP_VIDEO_URL
     * @param param2 ARG_STEP_DESCRIPTION
     * @return A new instance of fragment StepInstructFragment.
     */

    public static StepInstructFragment newInstance(String param1, String param2) {
        StepInstructFragment fragment = new StepInstructFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STEP_VIDEO_URL, param1);
        args.putString(ARG_STEP_DESCRIPTION, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStepVideoUrl = getArguments().getString(ARG_STEP_VIDEO_URL);
            mStepDescription = getArguments().getString(ARG_STEP_DESCRIPTION);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_instruct,container,false);
        unbinder = ButterKnife.bind(this,view);

        return inflater.inflate(R.layout.fragment_step_instruct, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        refreshDisplay();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**根据内容更新UI显示*/
    private void refreshDisplay(){
        mStepDescriptionView.setText(mStepDescription);
        Log.v(LOG_TAG,mStepDescription);
        initExoplayer();
    }

    private void initExoplayer(){

        // 1. Create a default TrackSelector
        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector( videoTrackSelectionFactory);

        // 2. Create a default LoadControl
        LoadControl loadControl = new DefaultLoadControl();

        // 3. Create the player
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);


        mSimpleExoPlayerView.setPlayer(player);

        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), "yourApplicationName"), defaultBandwidthMeter);
        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(mStepVideoUrl), dataSourceFactory, extractorsFactory, null, null);
        // Prepare the player with the source.
        player.prepare(videoSource);

    }

}

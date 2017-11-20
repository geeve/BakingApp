package com.example.android.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;

import com.example.android.bakingapp.UI.StepInstructFragment;
import com.example.android.bakingapp.UI.StepsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepDescriptionActivity extends AppCompatActivity {

    private String mCurrentVideoUrl;
    private String mCurrentStepDescription;

    private String mNextVideoUrl;
    private String mNextStepDescription;

    private static final String FAKE_VALE = "FAKE";

    private static final String LOG_TAG = StepDescriptionActivity.class.getSimpleName();

    @BindView(R.id.next_step)
    Button mNextStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_description);
        ButterKnife.bind(this);
        getBundles();
        initView();
    }

    private void getBundles(){
        Bundle b = getIntent().getExtras();
        this.mCurrentVideoUrl = b.getString(StepsAdapter.CURRENT_VIDEO_URL);
        this.mCurrentStepDescription = b.getString(StepsAdapter.CURRENT_STEP_DES);
        this.mNextVideoUrl = b.getString(StepsAdapter.NEXT_VIDEO_URL,FAKE_VALE);
        this.mNextStepDescription = b.getString(StepsAdapter.NEXT_STEP_DES,FAKE_VALE);
        Log.v(LOG_TAG,this.mCurrentStepDescription);
    }

    private void initView(){
        FragmentManager fragmentManager = getSupportFragmentManager();

        StepInstructFragment stepInstructFragment = StepInstructFragment.newInstance(mCurrentVideoUrl,mCurrentStepDescription);

        fragmentManager.beginTransaction()
                .replace(R.id.step_description_fragment,stepInstructFragment)
                .commit();

        if(FAKE_VALE.equals(mNextStepDescription)){
            mNextStep.setText("Step Over");
        }else{
            mNextStep.setText("Next Step");
        }
    }

    @OnClick(R.id.next_step)
    public void nextStep(){
        if(!FAKE_VALE.equals(mNextStepDescription)){
//            FragmentManager fm = getSupportFragmentManager();
//
//            StepInstructFragment stepInstructFragment = StepInstructFragment.newInstance(mNextVideoUrl,mNextStepDescription);
//
//            fm.beginTransaction()
//                    .replace(R.id.step_description_fragment,stepInstructFragment)
//                    .commit();
        }
    }
}

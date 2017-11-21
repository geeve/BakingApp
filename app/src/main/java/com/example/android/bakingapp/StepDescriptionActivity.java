package com.example.android.bakingapp;

import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.example.android.bakingapp.UI.StepInstructFragment;
import com.example.android.bakingapp.UI.StepsAdapter;
import com.example.android.bakingapp.data.RecipeContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.security.AccessController.getContext;

public class StepDescriptionActivity extends AppCompatActivity {

    private String mCurrentRecipeId;
    private String mCurrentStepId;

    private String mCurrentStepVideoUrl;
    private String mCurrentStepDescription;
    private Cursor mCursor;

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
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /***
     * 获得从Intent传过来的参数
     */
    private void getBundles(){
        Bundle b = getIntent().getExtras();
        this.mCurrentRecipeId = b.getString(StepsAdapter.CURRENT_RECIPE_ID);
        this.mCurrentStepId = b.getString(StepsAdapter.CURRENT_STEP_ID);
        getData();
        Log.v(LOG_TAG,this.mCurrentRecipeId);
    }

    private void initView(){
        FragmentManager fragmentManager = getSupportFragmentManager();

        StepInstructFragment stepInstructFragment = StepInstructFragment.newInstance(mCurrentStepVideoUrl,mCurrentStepDescription);

        fragmentManager.beginTransaction()
                .replace(R.id.step_description_fragment,stepInstructFragment)
                .commit();

        if(Integer.valueOf(mCurrentStepId) == mCursor.getCount() - 1){
            mNextStep.setText("Step Over");
        }else{
            mNextStep.setText("Next Step");
        }
        setTitle(mCursor.getString(mCursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_STEPS_SHORT_DESCRIPTION)));
    }

    /***
     * 根据RecipeId和StepId得到
     */
    private void getData(){
        mCursor = getContentResolver().query(RecipeContract.StepEntry.CONTENT_URI,
                null,
                RecipeContract.StepEntry.COLUMN_RECIPE_ID + "=" + mCurrentRecipeId,
                null,
                null);
        mCursor.moveToPosition(Integer.valueOf(mCurrentStepId));
        mCurrentStepVideoUrl = mCursor.getString(mCursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_STEPS_VIDEO_URL));
        mCurrentStepDescription = mCursor.getString(mCursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_STEPS_DESCRIPTION));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.next_step)
    public void nextStep(){
        if(!(Integer.valueOf(mCurrentStepId) == mCursor.getCount() - 1)){
            mCursor.moveToNext();
            mCurrentStepVideoUrl = mCursor.getString(mCursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_STEPS_VIDEO_URL));
            mCurrentStepDescription = mCursor.getString(mCursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_STEPS_DESCRIPTION));
            mCurrentStepId = mCursor.getString(mCursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_STEPS_ID));
            initView();
            Log.v(LOG_TAG,mCurrentStepDescription);
        }
    }
}

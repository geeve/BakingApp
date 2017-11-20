package com.example.android.bakingapp.UI;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.StepDescriptionActivity;
import com.example.android.bakingapp.data.RecipeContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/19 0019.
 * com.example.android.bakingapp.UI,BakingApp
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.SetpViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public static final String CURRENT_VIDEO_URL = "current_video_url";
    public static final String CURRENT_STEP_DES = "current_step_description";
    public static final String NEXT_VIDEO_URL = "next_video_url";
    public static final String NEXT_STEP_DES = "next_step_description";

    public StepsAdapter(Context context) {
        super();
        this.mContext = context;
    }

    @Override
    public StepsAdapter.SetpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_recipe_detail_step_item,parent,false);
        view.setFocusable(true);
        return new SetpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StepsAdapter.SetpViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        String currentVideoUrl = mCursor.getString(mCursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_STEPS_VIDEO_URL));
        String currentStepDescription = mCursor.getString(mCursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_STEPS_DESCRIPTION));
        String nextVideoUrl,nextStepDescription;
        if(mCursor.moveToNext()) {
            nextVideoUrl = mCursor.getString(mCursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_STEPS_VIDEO_URL));
            nextStepDescription = mCursor.getString(mCursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_STEPS_DESCRIPTION));
        }else {
            nextVideoUrl = null;
            nextStepDescription = null;
        }

        Bundle b = new Bundle();
        b.putString(CURRENT_VIDEO_URL,currentVideoUrl);
        b.putString(CURRENT_STEP_DES,currentStepDescription);
        b.putString(NEXT_VIDEO_URL,nextVideoUrl);
        b.putString(NEXT_STEP_DES,nextStepDescription);
        holder.stepView.setText(mCursor.getString(mCursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_STEPS_SHORT_DESCRIPTION)));
        holder.stepView.setTag(b);
        holder.stepView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = (Bundle) holder.stepView.getTag();
                Intent intent = new Intent(mContext, StepDescriptionActivity.class);
                intent.putExtras(bundle);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mCursor == null) {
            return 0;
        }

        return mCursor.getCount();
    }

    public void swapData(Cursor cursor){
        this.mCursor = cursor;
        notifyDataSetChanged();
    }
    class SetpViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.step_list_item)
        TextView stepView;
        public SetpViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

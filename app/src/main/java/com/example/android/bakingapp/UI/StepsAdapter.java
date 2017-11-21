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

    public static final String CURRENT_RECIPE_ID = "current_recipe_id";
    public static final String CURRENT_STEP_ID = "current_step_id";


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

        /**将当前食谱的ID和步骤ID作为参数传递到步骤详情Activity*/
        String currentRecipeId = mCursor.getString(mCursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_RECIPE_ID));
        String currentStepId = mCursor.getString(mCursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_STEPS_ID));

        Bundle b = new Bundle();
        b.putString(CURRENT_RECIPE_ID,currentRecipeId);
        b.putString(CURRENT_STEP_ID,currentStepId);

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

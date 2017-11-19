package com.example.android.bakingapp.UI;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
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
    public void onBindViewHolder(StepsAdapter.SetpViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        holder.stepView.setText(mCursor.getString(mCursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_STEPS_SHORT_DESCRIPTION)));
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

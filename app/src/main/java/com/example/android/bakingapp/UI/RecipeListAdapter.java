package com.example.android.bakingapp.UI;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipeActivity;
import com.example.android.bakingapp.data.RecipeContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/19 0019.
 * com.example.android.bakingapp.UI,BakingApp
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder> {

    private Context mContext;

    private Cursor mCursor;

    public RecipeListAdapter(Context context) {
        super();
        this.mContext = context;
    }

    @Override
    public RecipeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_recipe_list_item,parent,false);
        view.setFocusable(true);
        return new RecipeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeListViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        switch (position){
            case 0:
                holder.recipeImgView.setImageResource(R.drawable.nutella_pie);
                break;
            case 1:
                holder.recipeImgView.setImageResource(R.drawable.brownies);
                break;
            case 2:
                holder.recipeImgView.setImageResource(R.drawable.yellow_cake);
                break;
            case 3:
                holder.recipeImgView.setImageResource(R.drawable.cheesecake);
                break;
            default:
        }
        holder.recipeNameView.setText(mCursor.getString(mCursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME)));
        holder.recipeImgView.setTag(mCursor.getInt(mCursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_RECIPE_ID)));
        holder.recipeImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecipeActivity.class);
                intent.putExtra(RecipeContract.RecipeEntry.COLUMN_RECIPE_ID,(int)holder.recipeImgView.getTag());
                intent.putExtra(RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME,holder.recipeNameView.getText());
                mContext.startActivity(intent);
            }
        });
    }

    public void swapData(Cursor cursor){
        this.mCursor = cursor;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(mCursor == null) {
            return 0;
        }

        return mCursor.getCount();
    }

    class RecipeListViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.recipe_item_name)
        TextView recipeNameView;
        @BindView(R.id.recipe_item_image)
        ImageView recipeImgView;

        public RecipeListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

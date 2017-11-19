package com.example.android.bakingapp.UI;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
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
    public void onBindViewHolder(RecipeListViewHolder holder, int position) {
        mCursor.moveToFirst();

        holder.recipeNameView.setText(mCursor.getString(mCursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME)));
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

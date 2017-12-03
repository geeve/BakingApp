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

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public IngredientAdapter(Context context) {
        super();
        this.mContext = context;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.fragment_recipe_detail_ingredient_item,parent,false);
        rootView.setFocusable(true);
        return new IngredientViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.ingredientView.setText("• " +mCursor.getString(mCursor.getColumnIndex(RecipeContract.IngredientEntry.COLUMN_INGREDIENT_INGREDIENT_CONTENT)) +
        ":" + mCursor.getFloat(mCursor.getColumnIndex(RecipeContract.IngredientEntry.COLUMN_INGREDIENT_QUANTITY)) +
        " " +mCursor.getString(mCursor.getColumnIndex(RecipeContract.IngredientEntry.COLUMN_INGREDIENT_MEASURE)));
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
    class IngredientViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.ingredient_list_item)
        TextView ingredientView;
        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

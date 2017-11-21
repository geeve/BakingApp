package com.example.android.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.android.bakingapp.UI.RecipeDetailFragment;
import com.example.android.bakingapp.data.RecipeContract;

public class RecipeActivity extends AppCompatActivity {

    private int mRecipeId;
    private String mRecipeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        getBundle();
        initView();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void getBundle(){
        mRecipeId = getIntent().getIntExtra(RecipeContract.RecipeEntry.COLUMN_RECIPE_ID,1);
        mRecipeName = getIntent().getStringExtra(RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME);
    }

    private void initView(){
        FragmentManager fm = getSupportFragmentManager();

        RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(mRecipeId,mRecipeName);

        fm.beginTransaction()
                .replace(R.id.recipe_detail,recipeDetailFragment)
                .commit();
        setTitle(mRecipeName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

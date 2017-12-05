package com.example.android.bakingapp;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.UI.RecipeListFragment;
import com.example.android.bakingapp.data.RecipesIdlingResource;
import com.example.android.bakingapp.sync.RecipeSyncUtil;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity implements RecipeListFragment.getIdleResourse{

    @Nullable
    private RecipesIdlingResource idlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecipeSyncUtil.initialize(this);
        initView();

    }

    private void initView(){
        FragmentManager fm = getSupportFragmentManager();
        RecipeListFragment recipeListFragment = RecipeListFragment.newInstance();

        fm.beginTransaction()
                .replace(R.id.recipe_list_view,recipeListFragment)
                .commit();
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new RecipesIdlingResource();
        }
        return idlingResource;
    }

    @Override
    public RecipesIdlingResource getIdleRes() {
        return idlingResource;
    }
}

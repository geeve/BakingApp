package com.example.android.bakingapp;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.UI.RecipeListFragment;
import com.example.android.bakingapp.sync.RecipeSyncUtil;

public class MainActivity extends AppCompatActivity implements RecipeListFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecipeSyncUtil.initialize(this);
        initView();

    }

    private void initView(){
        FragmentManager fm = getSupportFragmentManager();
        RecipeListFragment recipeListFragment = RecipeListFragment.newInstance(null,null);

        fm.beginTransaction()
                .replace(R.id.recipe_list_view,recipeListFragment)
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

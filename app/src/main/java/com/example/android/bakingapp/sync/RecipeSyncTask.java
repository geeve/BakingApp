package com.example.android.bakingapp.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.android.bakingapp.Model.RecipeModel;
import com.example.android.bakingapp.utilites.NetWorkUtils;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/18 0018.
 * com.example.android.bakingapp.sync,BakingApp
 */

public class RecipeSyncTask {
    synchronized public static void syncRecipe(Context context){
        try{
            URL recipeRequestUrl = NetWorkUtils.createRequestUrl();

            ArrayList<RecipeModel> recipeList = NetWorkUtils.getRecipeModelFromJson(NetWorkUtils.makeHttpRequest(recipeRequestUrl));

            if(recipeList != null && recipeList.size() != 0){
                //TODO:将数据同步到数据库中


            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

package com.example.android.bakingapp.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.android.bakingapp.Model.IngredientModel;
import com.example.android.bakingapp.Model.RecipeModel;
import com.example.android.bakingapp.Model.RecipeStepModel;
import com.example.android.bakingapp.data.RecipeContract;
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
                /**将数据同步到数据库中*/
                ContentResolver recipeResolver = context.getContentResolver();

                recipeResolver.bulkInsert(RecipeContract.RecipeEntry.CONTENT_URI,getRecipeValues(recipeList));
                for (RecipeModel recipe : recipeList) {
                    recipeResolver.bulkInsert(RecipeContract.IngredientEntry.CONTENT_URI,getIngredientValues(recipe.getmIngredientList(),recipe.getmRecipeId()));
                    recipeResolver.bulkInsert(RecipeContract.StepEntry.CONTENT_URI,getStepsValues(recipe.getmRecipeStepList(),recipe.getmRecipeId()));
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /***
     * 将数据从Model转化成ContentValues
     * @param recipes
     * @return
     */
    public static ContentValues[] getRecipeValues(ArrayList<RecipeModel> recipes){
        ContentValues[] contentValues = new ContentValues[recipes.size()];
        for (int i = 0; i < contentValues.length; i++) {
            ContentValues values = new ContentValues();
            values.put(RecipeContract.RecipeEntry.COLUMN_RECIPE_ID,recipes.get(i).getmRecipeId());
            values.put(RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME,recipes.get(i).getmRecipName());
            values.put(RecipeContract.RecipeEntry.COLUMN_RECIPE_IMG,recipes.get(i).getmRecipeImage());
            values.put(RecipeContract.RecipeEntry.COLUMN_RECIPE_SERVING,recipes.get(i).getmRecipeServings());

            contentValues[i] = values;
        }
        return contentValues;
    }


    /***
     *
     * @param ingredients 佐料的List
     * @param recipeId 此佐料属于的食谱
     * @return
     */
    public static ContentValues[] getIngredientValues(ArrayList<IngredientModel> ingredients,int recipeId){
        ContentValues[] contentValues = new ContentValues[ingredients.size()];
        for (int i = 0; i < contentValues.length; i++) {
            ContentValues values = new ContentValues();
            values.put(RecipeContract.IngredientEntry.COLUMN_RECIPE_ID,recipeId);
            values.put(RecipeContract.IngredientEntry.COLUMN_INGREDIENT_QUANTITY,ingredients.get(i).getmIngredientQuantity());
            values.put(RecipeContract.IngredientEntry.COLUMN_INGREDIENT_MEASURE,ingredients.get(i).getmIngredientMeasure());
            values.put(RecipeContract.IngredientEntry.COLUMN_INGREDIENT_INGREDIENT_CONTENT,ingredients.get(i).getmIngredients());
            contentValues[i] = values;
        }
        return contentValues;
    }

    public static ContentValues[] getStepsValues(ArrayList<RecipeStepModel> steps,int recipeId){
        ContentValues[] contentValues = new ContentValues[steps.size()];
        for (int i = 0; i < contentValues.length; i++) {
            ContentValues values = new ContentValues();
            values.put(RecipeContract.StepEntry.COLUMN_RECIPE_ID,recipeId);
            values.put(RecipeContract.StepEntry.COLUMN_STEPS_ID,steps.get(i).getmStepId());
            values.put(RecipeContract.StepEntry.COLUMN_STEPS_SHORT_DESCRIPTION,steps.get(i).getmStepShortDescription());
            values.put(RecipeContract.StepEntry.COLUMN_STEPS_DESCRIPTION,steps.get(i).getmStepDescription());
            values.put(RecipeContract.StepEntry.COLUMN_STEPS_THUMBNAIL_URL,steps.get(i).getmStepThumbnailURL());
            values.put(RecipeContract.StepEntry.COLUMN_STEPS_VIDEO_URL,steps.get(i).getmStepVideoURL());
            contentValues[i] = values;
        }
        return contentValues;
    }
}

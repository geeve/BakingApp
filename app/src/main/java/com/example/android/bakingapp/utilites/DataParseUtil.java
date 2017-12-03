package com.example.android.bakingapp.utilites;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.example.android.bakingapp.Model.IngredientModel;
import com.example.android.bakingapp.Model.RecipeModel;
import com.example.android.bakingapp.Model.RecipeStepModel;
import com.example.android.bakingapp.data.RecipeContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/3 0003.
 * com.example.android.bakingapp.utilites,BakingApp
 */

public class DataParseUtil {

    /***
     * 根据recipeId查询佐料清单
     * @param recipeId int
     * @return list of ingredients
     */
    public static ArrayList<IngredientModel> getIngredients(Context context,int recipeId){
        ArrayList<IngredientModel> ingredientModels =  new ArrayList<>();

        Uri uri = RecipeContract.IngredientEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(recipeId)).build();
        Cursor cursor = context.getContentResolver().query(
                uri,
                null,
                null,
                null,
                null
        );

        try {
            if(cursor == null){
                return null;
            }
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                IngredientModel ingredient = new IngredientModel(cursor.getFloat(cursor.getColumnIndex(RecipeContract.IngredientEntry.COLUMN_INGREDIENT_QUANTITY))
                ,cursor.getString(cursor.getColumnIndex(RecipeContract.IngredientEntry.COLUMN_INGREDIENT_MEASURE))
                ,cursor.getString(cursor.getColumnIndex(RecipeContract.IngredientEntry.COLUMN_INGREDIENT_INGREDIENT_CONTENT)));
                ingredientModels.add(ingredient);
                cursor.moveToNext();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }finally {
            if(cursor != null) {
                cursor.close();
            }
        }
        return ingredientModels;
    }

    /***
     * 根据recipeId返回步骤列表
     * @param context context
     * @param recipeId int id
     * @return list of steps
     */
    private static ArrayList<RecipeStepModel> getSteps(Context context, int recipeId){
        ArrayList<RecipeStepModel> steps = new ArrayList<>();

        Uri uri = RecipeContract.StepEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(recipeId)).build();
        Cursor cursor = context.getContentResolver().query(
                uri,
                null,
                null,
                null,
                null
        );
        try {
            if(cursor == null){
                return null;
            }
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                RecipeStepModel recipeStep = new RecipeStepModel(cursor.getInt(cursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_STEPS_ID)),
                        cursor.getString(cursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_STEPS_SHORT_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_STEPS_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_STEPS_VIDEO_URL)),
                        cursor.getString(cursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_STEPS_THUMBNAIL_URL)));
                steps.add(recipeStep);
                cursor.moveToNext();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }finally {
            if(cursor != null) {
                cursor.close();
            }
        }
        return steps;
    }

    /***
     * 获得Recipe列表
     * @param context context
     * @return arraylist of recipemodels
     */
    public static ArrayList<RecipeModel> getRecipes(Context context){
        ArrayList<RecipeModel> recipes = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(
                RecipeContract.RecipeEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        try {
            if(cursor == null){
                return null;
            }
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int recipeId = cursor.getInt(cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_RECIPE_ID));
                String recipeName = cursor.getString(cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME));
                int recipeServing = cursor.getInt(cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_RECIPE_SERVING));
                ArrayList<IngredientModel> ingredients = getIngredients(context, recipeId);
                ArrayList<RecipeStepModel> steps = getSteps(context, recipeId);

                RecipeModel recipe = new RecipeModel(recipeId, recipeName, ingredients, steps, recipeServing, null);
                recipes.add(recipe);
                cursor.moveToNext();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }finally {
            if(cursor != null) {
                cursor.close();
            }
        }

        return recipes;
    }

    /***
     * 根据recipeName获得recipeId
     * @param context context
     * @return int id
     */
    public static int getRecipeIdFromeRecipeName(Context context,String recipeName){
        ArrayList<RecipeModel> recipes = getRecipes(context);
        int recipeId = 0;
        for(RecipeModel recipeModel:recipes){
            if(recipeName.equals(recipeModel.getmRecipName())){
                recipeId = recipeModel.getmRecipeId();
                return recipeId;
            }
        }

        return recipeId;
    }
}

package com.example.android.bakingapp.Model;

import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 *
 * @author Administrator
 * @date 2017/11/17 0017
 * com.example.android.bakingapp.Model,BakingApp
 */

public class RecipeModel {

    /**
     * 菜谱JSON数据中的ID
     */
    private int mRecipeId;

    /**
     * 菜谱JSON中的菜谱名称
     */
    private String mRecipName;

    /**
     * 菜谱所需佐料
     */
    private ArrayList<IngredientModel> mIngredientList;

    /**
     * 菜谱步骤集合
     */
    private ArrayList<RecipeStepModel> mRecipeStepList;

    /**
     * 份数
     */
    private int mRecipeServings;

    /**
     * 菜谱图片
     */
    private String mRecipeImage;

    /**
     * @param id
     * @param name
     * @param ingredients
     * @param recipeSteps
     * @param servinigs
     * @param imageUrl
     */
    public RecipeModel(int id, String name, ArrayList<IngredientModel> ingredients, ArrayList<RecipeStepModel> recipeSteps, int servinigs, @Nullable String imageUrl) {
        this.mIngredientList = ingredients;
        this.mRecipeId = id;
        this.mRecipeImage = imageUrl;
        this.mRecipeServings = servinigs;
        this.mRecipeStepList = recipeSteps;
        this.mRecipName = name;
    }

    public int getmRecipeId() {
        return mRecipeId;
    }

    public void setmRecipeId(int mRecipeId) {
        this.mRecipeId = mRecipeId;
    }

    public String getmRecipName() {
        return mRecipName;
    }

    public void setmRecipName(String mRecipName) {
        this.mRecipName = mRecipName;
    }

    public ArrayList<IngredientModel> getmIngredientList() {
        return mIngredientList;
    }

    public ArrayList<RecipeStepModel> getmRecipeStepList() {
        return mRecipeStepList;
    }

    public void setmRecipeStepList(ArrayList<RecipeStepModel> mRecipeStepList) {
        this.mRecipeStepList = mRecipeStepList;
    }

    public int getmRecipeServings() {
        return mRecipeServings;
    }

    public void setmRecipeServings(int mRecipeServings) {
        this.mRecipeServings = mRecipeServings;
    }

    public String getmRecipeImage() {
        return mRecipeImage;
    }

    public void setmRecipeImage(String mRecipeImage) {
        this.mRecipeImage = mRecipeImage;
    }
}

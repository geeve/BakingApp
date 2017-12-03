package com.example.android.bakingapp.Model;

/**
 *
 * @author Administrator
 * @date 2017/11/17 0017
 * com.example.android.bakingapp.Model,BakingApp
 * 佐料Model
 */

public class IngredientModel {

    /**
     * 佐料用量
     */
    private float mIngredientQuantity;

    /**
     * 用量单位
     */
    private String mIngredientMeasure;

    /**
     * 佐料组成
     */
    private String mIngredients;


    /**
     * @param quantity 用量
     * @param measure  单位
     * @param ingredients  用料
     */
    public IngredientModel(float quantity,String measure,String ingredients) {
        this.mIngredientMeasure = measure;
        this.mIngredientQuantity = quantity;
        this.mIngredients = ingredients;
    }

    public float getmIngredientQuantity() {
        return mIngredientQuantity;
    }

    public void setmIngredientQuantity(int mIngredientQuantity) {
        this.mIngredientQuantity = mIngredientQuantity;
    }

    public String getmIngredientMeasure() {
        return mIngredientMeasure;
    }

    public void setmIngredientMeasure(String mIngredientMeasure) {
        this.mIngredientMeasure = mIngredientMeasure;
    }

    public String getmIngredients() {
        return mIngredients;
    }

    public void setmIngredients(String mIngredients) {
        this.mIngredients = mIngredients;
    }
}

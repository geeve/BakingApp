package com.example.android.bakingapp.Model;

/**
 *
 * @author Administrator
 * @date 2017/11/17 0017
 * com.example.android.bakingapp.Model,BakingApp
 */

public class RecipeStepModel {

    /**
     * 菜谱步骤ID
     */
    private int mStepId;

    /**
     * 步骤简要描述，类似标题
     */
    private String mStepShortDescription;

    /**
     * 步骤描述
     */
    private String mStepDescription;

    /**
     * 该步骤视频地址
     */
    private String mStepVideoURL;

    /**
     * 该步骤缩略图地址
     */
    private String mStepThumbnailURL;


    /**
     * @param id
     * @param shortDesc
     * @param description
     * @param videoUrl
     * @param stepThumbnailUrl
     */
    public RecipeStepModel(int id,String shortDesc,String description,String videoUrl,String stepThumbnailUrl) {
        this.mStepId =id;
        this.mStepDescription = description;
        this.mStepShortDescription = shortDesc;
        this.mStepThumbnailURL = stepThumbnailUrl;
        this.mStepVideoURL = videoUrl;
    }
}

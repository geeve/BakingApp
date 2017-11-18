package com.example.android.bakingapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Administrator on 2017/11/18 0018.
 * com.example.android.bakingapp.data,BakingApp
 */

public class RecipeContract {


    public static final String CONTENT_AUTHORITY = "com.example.android.bakingapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_RECIPE = "recipes";

    public static final String PATH_INGREDIENT = "ingredients";

    public static final String PATH_STEP = "steps";

    /** Inner class that defines the table contents of the recipes table */
    public static final class RecipeEntry implements BaseColumns{
        /** The base CONTENT_URI used to query the Recipes table from the content provider */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_RECIPE)
                .build();

        public static final String TABLE_NAME = "recipes";

        public static final String COLUMN_RECIPE_NAME = "recipe_name";

        public static final String COLUMN_RECIPE_ID = "recipe_id";

        public static final String COLUMN_RECIPE_SERVING = "recipe_servings";

        public static final String COLUMN_RECIPE_IMG = "recipe_img";
    }

    /**Inner class that defines the table contents of the ingredient table*/
    public static final class IngredientEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_INGREDIENT)
                .build();

        public static final String TABLE_NAME = "ingredients";

        public static final String COLUMN_RECIPE_ID = "recipe_id";

        public static final String COLUMN_INGREDIENT_QUANTITY = "ingredient_quantity";

        public static final String COLUMN_INGREDIENT_MEASURE = "ingredient_measure";

        public static final String COLUMN_INGREDIENT_INGREDIENT_CONTENT = "ingredient_content";

    }

    /**Inner class that defines the table contents of the steps table*/
    public static final class StepEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_STEP)
                .build();
        public static final String TABLE_NAME = "steps";

        public static final String COLUMN_RECIPE_ID = "recipe_id";

        public static final String  COLUMN_STEPS_ID = "step_id";

        public static final String COLUMN_STEPS_SHORT_DESCRIPTION ="step_short_description";

        public static final String COLUMN_STEPS_DESCRIPTION  = "step_description";

        public static final String COLUMN_STEPS_VIDEO_URL ="step_video_url";

        public static final String COLUMN_STEPS_THUMBNAIL_URL ="step_thumbnail_url";

    }
}

package com.example.android.bakingapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.version;

/**
 * Created by Administrator on 2017/11/18 0018.
 * com.example.android.bakingapp.data,BakingApp
 */

public class RecipeDbHelper extends SQLiteOpenHelper {

    /**
     * This is the name of our database. Database names should be descriptive and end with the
     * .db extension.
     */
    public static final String DATABASE_NAME = "recipes.db";

    /**The version of the database*/
    public static final int DATABASE_VERSION = 1;

    public RecipeDbHelper(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_RECIPE_TABLE =
                "CREATE TABLE " + RecipeContract.RecipeEntry.TABLE_NAME + "(" +
                        RecipeContract.RecipeEntry._ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        RecipeContract.RecipeEntry.COLUMN_RECIPE_ID + " INTEGER, " +
                        RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME + " TEXT NOT NULL, " +
                        RecipeContract.RecipeEntry.COLUMN_RECIPE_SERVING + " INTEGER, " +
                        RecipeContract.RecipeEntry.COLUMN_RECIPE_IMG + " TEXT);";
        db.execSQL(SQL_CREATE_RECIPE_TABLE);

        final String SQL_CREATE_INGREDIENT_TABLE =
                "CREATE TABLE " + RecipeContract.IngredientEntry.TABLE_NAME + "(" +
                        RecipeContract.IngredientEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        RecipeContract.IngredientEntry.COLUMN_RECIPE_ID + " INTEGER NOT NULL, " +
                        RecipeContract.IngredientEntry.COLUMN_INGREDIENT_QUANTITY + " INTEGER NOT NULL," +
                        RecipeContract.IngredientEntry.COLUMN_INGREDIENT_MEASURE + "TEXT, " +
                        RecipeContract.IngredientEntry.COLUMN_INGREDIENT_INGREDIENT_CONTENT + " TEXT);";
        db.execSQL(SQL_CREATE_INGREDIENT_TABLE);

        final String SQL_CREATE_STEPS_TABLE =
                "CREATE TABLE " + RecipeContract.StepEntry.TABLE_NAME + "(" +
                        RecipeContract.StepEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        RecipeContract.StepEntry.COLUMN_RECIPE_ID + "INTEGER NOT NULL, "+
                        RecipeContract.StepEntry.COLUMN_STEPS_ID + "INTEGER NOT NULL, "+
                        RecipeContract.StepEntry.COLUMN_STEPS_SHORT_DESCRIPTION + "TEXT, "+
                        RecipeContract.StepEntry.COLUMN_STEPS_DESCRIPTION + "TEXT, " +
                        RecipeContract.StepEntry.COLUMN_STEPS_VIDEO_URL + "TEXT, " +
                        RecipeContract.StepEntry.COLUMN_STEPS_THUMBNAIL_URL + "TEXT);";

        db.execSQL(SQL_CREATE_STEPS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != 1) {
            db.execSQL("DROP TABLE IF EXISTS " + RecipeContract.RecipeEntry.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + RecipeContract.IngredientEntry.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + RecipeContract.StepEntry.TABLE_NAME);
            onCreate(db);
        }
    }
}

package com.example.android.bakingapp.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2017/11/18 0018.
 * com.example.android.bakingapp.data,BakingApp
 */

public class RecipeProvider extends ContentProvider {

    public static final int CODE_RECIPE = 100;
    public static final int CODE_RECIPE_WITH_ID = 101;

    public static final int CODE_INGREDIENT = 200;
    public static final int CODE_INGREDIENT_WITH_ID = 201;

    public static final int CODE_STEPS = 300;
    public static final int CODE_STEP_WITH_ID = 301;


    private RecipeDbHelper mRecipeDbHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    /**
     * Creates the UriMatcher that will match each URI to the CODE_RECIPE and
     * CODE_RECIPE_WITH_ID constants defined above.
     *
     * @return A UriMatcher that correctly matches the constants for CODE_RECIPE and CODE_RECIPE_WITH_ID
     */
    public static UriMatcher buildUriMatcher(){

        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        final String authority = RecipeContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, RecipeContract.PATH_RECIPE,CODE_RECIPE);

        uriMatcher.addURI(authority,RecipeContract.PATH_RECIPE + "/#",CODE_RECIPE_WITH_ID);

        uriMatcher.addURI(authority, RecipeContract.PATH_INGREDIENT,CODE_INGREDIENT);

        uriMatcher.addURI(authority,RecipeContract.PATH_INGREDIENT + "/#",CODE_INGREDIENT_WITH_ID);

        uriMatcher.addURI(authority, RecipeContract.PATH_STEP,CODE_STEPS);

        uriMatcher.addURI(authority,RecipeContract.PATH_STEP + "/#",CODE_STEP_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mRecipeDbHelper = new RecipeDbHelper(getContext());
        return false;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        SQLiteDatabase db = mRecipeDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rowInsert = 0;
        switch (match){
            case CODE_RECIPE:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(RecipeContract.RecipeEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowInsert++;
                        }
                    }

                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
                break;
            case CODE_INGREDIENT:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(RecipeContract.IngredientEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowInsert++;
                        }
                    }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
                break;
            case CODE_STEPS:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(RecipeContract.StepEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowInsert++;
                        }
                    }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
                break;
            default:
                return super.bulkInsert(uri, values);
        }
        return rowInsert;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}

package com.example.android.bakingapp.sync;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.example.android.bakingapp.data.RecipeContract;

/**
 * Created by Administrator on 2017/11/19 0019.
 * com.example.android.bakingapp.sync,BakingApp
 */

public class RecipeSyncUtil {
    private static boolean sInitialized;

    synchronized public static void initialize(@NonNull final Context context){
        if(sInitialized){
            return;
        }

        sInitialized = true;

        Thread checkForEmpty = new Thread(new Runnable() {
            @Override
            public void run() {
                String[] projection = {RecipeContract.RecipeEntry._ID};

                Cursor cursor = context.getContentResolver().query(
                        RecipeContract.RecipeEntry.CONTENT_URI,
                        projection,
                        null,
                        null,
                        null);

                if(cursor == null || cursor.getCount() == 0){
                    startImmediateSync(context);
                }

                cursor.close();
            }
        });

        checkForEmpty.start();
    }

    public static void startImmediateSync(@NonNull final Context context){
        Intent intentToSynceImmediate = new Intent(context,RecipeSyncIntentService.class);
        context.startService(intentToSynceImmediate);
    }
}

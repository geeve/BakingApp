package com.example.android.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.bakingapp.Model.IngredientModel;
import com.example.android.bakingapp.Model.RecipeModel;
import com.example.android.bakingapp.data.RecipeContract;
import com.example.android.bakingapp.utilites.DataParseUtil;
import com.example.android.bakingapp.utilites.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeAppWidget extends AppWidgetProvider {

    public static final String  LOG_TAG = RecipeAppWidget.class.getSimpleName();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String recipeName, List<IngredientModel> ingredientModels) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget);
        views.setTextViewText(R.id.widget_recipe_name,recipeName);
        views.removeAllViews(R.id.widget_ingredients_container);
        for(IngredientModel ingredientModel : ingredientModels){
            RemoteViews ingredientView = new RemoteViews(context.getPackageName(),R.layout.recipe_app_widget_list_item);
            String line = StringUtil.formatIngdedient(context,ingredientModel.getmIngredients(),ingredientModel.getmIngredientQuantity(),ingredientModel.getmIngredientMeasure());
            ingredientView.setTextViewText(R.id.widget_ingredient_name,line);
            views.addView(R.id.widget_ingredients_container,ingredientView);
        }
        Intent intent = new Intent(context,RecipeActivity.class);
        intent.putExtra(RecipeContract.RecipeEntry.COLUMN_RECIPE_ID,DataParseUtil.getRecipeIdFromeRecipeName(context,recipeName));
        intent.putExtra(RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME,recipeName);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_ingredients_container,pendingIntent);
        Log.e(LOG_TAG,"onclick set");
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        ArrayList<RecipeModel> recipeModels = DataParseUtil.getRecipes(context);
        if(recipeModels == null){
            return;
        }
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeModels.get(0).getmRecipName(),
                    DataParseUtil.getIngredients(context,recipeModels.get(0).getmRecipeId()));
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


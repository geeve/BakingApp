package com.example.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.android.bakingapp.data.RecipeContract;
import com.example.android.bakingapp.utilites.DataParseUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 *
 * @author Administrator
 *
 * com.example.android.bakingapp,BakingApp
 */

public class WidgetConfigActivity extends AppCompatActivity {

    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    private static final String noDataErrorMessage = "Recipe data not exsit,please sync data first...";

    @BindView(R.id.widget_config_radioGroup)
    RadioGroup namesRadioGroup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setResult(RESULT_CANCELED);
        setContentView(R.layout.widget_config_activity);
        ButterKnife.bind(this);

        /**First, get the App Widget ID from the Intent that launched the Activity*/
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

            if(mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID){
                finish();
            }
        }

        Cursor cursor = getContentResolver().query(
                RecipeContract.RecipeEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        if(cursor == null){
            Toast.makeText(this, noDataErrorMessage, Toast.LENGTH_SHORT).show();
            finish();
        }

        /***Fill the radioGroup*/
        try {
            if(cursor == null){
                return;
            }
            cursor.moveToFirst();

            for(int i = 0;i<cursor.getCount();i++){
                AppCompatRadioButton button = new AppCompatRadioButton(this);
                button.setText(cursor.getString(cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME)));
                button.setId(cursor.getInt(cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_RECIPE_ID)));
                button.setHeight(72);
                setupRadioButtonColor(button);
                namesRadioGroup.addView(button);
                cursor.moveToNext();
            }
            // Check the first item when loaded
            if (namesRadioGroup.getChildCount() > 0) {
                ((AppCompatRadioButton) namesRadioGroup.getChildAt(0)).setChecked(true);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }
    }

    @OnClick(R.id.widget_config_button)
    public void OnOkButtonClick(){
        int recipeId = namesRadioGroup.getCheckedRadioButtonId();
        String recipeName = ((AppCompatRadioButton) namesRadioGroup.getChildAt(recipeId-1)).getText().toString();

        Context context = getApplicationContext();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        RecipeAppWidget.updateAppWidget(this,appWidgetManager,mAppWidgetId,recipeName, DataParseUtil.getIngredients(this,recipeId));
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }

    private void setupRadioButtonColor(AppCompatRadioButton button) {

        int color = ContextCompat.getColor(this, R.color.colorPrimary);

        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{
                        color, color
                }
        );
        button.setSupportButtonTintList(colorStateList);
    }
}

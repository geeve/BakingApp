package com.example.android.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import static android.support.test.espresso.action.ViewActions.click;

import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.data.RecipeContract;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Administrator on 2017/12/5 0005.
 * com.example.android.bakingapp,BakingApp
 */
@RunWith(AndroidJUnit4.class)
public class RecipeListIntentTest {

    private static final int EXTRA_RECIPE_ID_VALUE = 1;


    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void clickOnRecyclerViewItem_runRecipeActivityIntent(){
        onView(ViewMatchers.withId(R.id.fragment_recipe_list_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        intended(
                hasExtra(RecipeContract.RecipeEntry.COLUMN_RECIPE_ID,EXTRA_RECIPE_ID_VALUE)
        );
    }
}

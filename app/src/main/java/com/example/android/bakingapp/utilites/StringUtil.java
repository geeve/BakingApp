package com.example.android.bakingapp.utilites;

import android.content.Context;

import com.example.android.bakingapp.R;

import java.util.Locale;

/**
 * Created by Administrator on 2017/12/3 0003.
 * com.example.android.bakingapp.utilites,BakingApp
 */

public class StringUtil {

    public static String formatIngdedient(Context context, String name, float quantity, String measure) {

        String line = context.getResources().getString(R.string.recipe_details_ingredient_line);

        String quantityStr = String.format(Locale.US, "%s", quantity);
        if (quantity == (long) quantity) {
            quantityStr = String.format(Locale.US, "%d", (long) quantity);
        }

        return String.format(Locale.US, line, name, quantityStr, measure);
    }
}

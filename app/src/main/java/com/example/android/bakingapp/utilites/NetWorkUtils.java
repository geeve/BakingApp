package com.example.android.bakingapp.utilites;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.android.bakingapp.Model.IngredientModel;
import com.example.android.bakingapp.Model.RecipeModel;
import com.example.android.bakingapp.Model.RecipeStepModel;
import com.example.android.bakingapp.data.Contract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/18 0018.
 * com.example.android.bakingapp.utilites,BakingApp
 */

public class NetWorkUtils {

    private static final String LOG_TAG = NetWorkUtils.class.getSimpleName();

    public static final String BASE_REQUEST_URL="https://gist.githubusercontent.com/Parorisim/121ba85695d4211fcd596e7d4583e492/raw/6ddafa1626c7e8a861b586fd863a31847c50baa6/android-baking-app.json";
    //"http://go.udacity.com/android-baking-app-json";


    /***
     * 将从网站得到的JSON字符串包装成ArrayList;
     * @param jsonResponse String
     * @return ArrayList<RecipeModel>
     */
    public static ArrayList<RecipeModel> getRecipeModelFromJson(String jsonResponse) throws JSONException {


        if(TextUtils.isEmpty(jsonResponse)){
           return null;
        }
        Log.v(LOG_TAG,jsonResponse);
        ArrayList<RecipeModel> recipeList = new ArrayList<>();

        JSONArray arrayJson = new JSONArray(jsonResponse);
        for (int i = 0; i < arrayJson.length(); i++) {

            /**获得每个食谱的JSON*/
            JSONObject itemJsonRecipe = arrayJson.getJSONObject(i);

            int recipeId = itemJsonRecipe.getInt(Contract.JsonResponseEntry.JSON_RESPONSE_RECIPE_ID);
            String recipeName = itemJsonRecipe.getString(Contract.JsonResponseEntry.JSON_RESPONSE_RECIPE_NAME);
            /**获得佐料*/
            JSONArray ingredients = itemJsonRecipe.getJSONArray(Contract.JsonResponseEntry.JSON_RESPONSE_INGREDIENTS);

            ArrayList<IngredientModel> ingredientList = new ArrayList<>();
            for (int j = 0; j < ingredients.length(); j++) {
                JSONObject itemJsonIngredient = ingredients.getJSONObject(j);

                int quantity = itemJsonIngredient.getInt(Contract.JsonResponseEntry.JSON_RESPONSE_INGREDIENTS_QUANTITY);
                String measure = itemJsonIngredient.getString(Contract.JsonResponseEntry.JSON_RESPONSE_INGREDIENTS_MEASURE);
                String ingredient = itemJsonIngredient.getString(Contract.JsonResponseEntry.JSON_RESPONSE_INGREDIENTS_INGREDIENT);

                ingredientList.add(new IngredientModel(quantity,measure,ingredient));
            }
            /**获得步骤*/
            JSONArray steps = itemJsonRecipe.getJSONArray(Contract.JsonResponseEntry.JSON_RESPONSE_STEPS);
            ArrayList<RecipeStepModel> recipeStepList = new ArrayList<>();
            for (int k = 0; k < steps.length(); k++) {
                JSONObject itemSteps = steps.getJSONObject(k);
                int stepId = itemSteps.getInt(Contract.JsonResponseEntry.JSON_RESPONSE_STEPS_ID);
                String shortDescription = itemSteps.getString(Contract.JsonResponseEntry.JSON_RESPONSE_STEPS_SHORTDESCRIPTION);
                String description = itemSteps.getString(Contract.JsonResponseEntry.JSON_RESPONSE_STEPS_DESCRIPTION);
                String videoUrl = itemSteps.getString(Contract.JsonResponseEntry.JSON_RESPONSE_STEPS_VIDEOURL);
                String thumbnailUrl = itemSteps.getString(Contract.JsonResponseEntry.JSON_RESPONSE_STEPS_THUMBNAILURL);

                recipeStepList.add(new RecipeStepModel(stepId,shortDescription,description,videoUrl,thumbnailUrl));
            }

            int servings = itemJsonRecipe.getInt(Contract.JsonResponseEntry.JSON_RESPONSE_SERVINGS);
            String imageUrl = itemJsonRecipe.getString(Contract.JsonResponseEntry.JSON_RESPONSE_IMGE);

            recipeList.add(new RecipeModel(recipeId,recipeName,ingredientList,recipeStepList,servings,imageUrl));
        }
        return recipeList;
    }


    /***
     * 获得网站返回的JSON格式数据
     * @param url 请求的URL
     * @return JSON格式的String
     */
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection httpUrlConnection = null;
        InputStream inputStream = null;

        try {
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setConnectTimeout(10000);
            httpUrlConnection.setReadTimeout(15000);
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.connect();

            if(httpUrlConnection.getResponseCode() == 200){
                inputStream = httpUrlConnection.getInputStream();

                /**从输入流中读取为String*/
                jsonResponse = readFromStream(inputStream);
            }else {
                Log.e(LOG_TAG,"Http error:"+httpUrlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG,"Retriew data error!",e);
        }finally {
            if(httpUrlConnection != null){
                httpUrlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    /***
     *  从InputStream中读取数据转化为String
     * @param inputStream
     * @return String
     * @throws IOException
     */
    @NonNull
    private static String readFromStream(InputStream inputStream)  throws IOException{
        StringBuilder output = new StringBuilder();

        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();
            while (line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }

        return output.toString();
    }

    /***
     * 生成request Url
     *
     * @return
     */
    public static URL createRequestUrl() {
        URL url = null;

        try {
            url = new URL(BASE_REQUEST_URL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG,"Create URL error!",e);
        }

        return url;
    }
}

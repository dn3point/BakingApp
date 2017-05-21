package com.iamzhaoyuan.android.baking.task;

import android.os.AsyncTask;
import android.util.Log;

import com.iamzhaoyuan.android.baking.dto.Ingredient;
import com.iamzhaoyuan.android.baking.dto.Recipe;
import com.iamzhaoyuan.android.baking.dto.RecipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchRecipesTask extends AsyncTask<Void, Void, List<Recipe>> {
    private static final String LOG_TAG = FetchRecipesTask.class.getName();

    public interface AsyncResponse {
        void processFinish(List<Recipe> recipes);
    }

    private AsyncResponse delegate = null;

    public FetchRecipesTask(AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected List<Recipe> doInBackground(Void... voids) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String recipesJsonStr = null;

        try {
            final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
            URL url = new URL(RECIPE_URL);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null) {
                return null;
            }
            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            recipesJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return getRecipeDataFromJson(recipesJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error", e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Recipe> recipes) {
        delegate.processFinish(recipes);
    }

    private List<Recipe> getRecipeDataFromJson(String recipesJsonStr) throws JSONException {
        final String NODE_RECIPE_ID = "id";
        final String NODE_NAME = "name";
        final String NODE_INGREDIENTS = "ingredients";
        final String NODE_QUANTITY = "quantity";
        final String NODE_MEASURE = "measure";
        final String NODE_INGREDIENT = "ingredient";
        final String NODE_STEPS = "steps";
        final String NODE_STEP_ID = "id";
        final String NODE_SHORT_DESC = "shortDescription";
        final String NODE_DESC = "description";
        final String NODE_VIDEO_URL = "videoURL";
        final String NODE_THUMBNAIL_URL = "thumbnailURL";

        JSONArray recipeArray = new JSONArray(recipesJsonStr);
        List<Recipe> recipes = new ArrayList<>(recipeArray.length());
        for (int i = 0; i < recipeArray.length(); i++) {
            JSONObject recipeObj = recipeArray.getJSONObject(i);
            int recipeId = recipeObj.getInt(NODE_RECIPE_ID);
            String recipeName = recipeObj.getString(NODE_NAME);
            JSONArray ingredientArray = recipeObj.getJSONArray(NODE_INGREDIENTS);
            List<Ingredient> ingredients = new ArrayList<>(ingredientArray.length());
            for (int j = 0; j < ingredientArray.length(); j++) {
                JSONObject ingredientObj = ingredientArray.getJSONObject(j);
                int quantity = ingredientObj.getInt(NODE_QUANTITY);
                String measure = ingredientObj.getString(NODE_MEASURE);
                String ingredient = ingredientObj.getString(NODE_INGREDIENT);
                Ingredient ingredientItem = new Ingredient(quantity, measure, ingredient);
                ingredients.add(ingredientItem);
            }
            JSONArray stepArray = recipeObj.getJSONArray(NODE_STEPS);
            List<RecipeStep> steps = new ArrayList<>(stepArray.length());
            for (int k = 0; k < stepArray.length(); k++) {
                JSONObject stepObj = stepArray.getJSONObject(k);
                int stepId = stepObj.getInt(NODE_STEP_ID);
                String shortDesc = stepObj.getString(NODE_SHORT_DESC);
                String desc = stepObj.getString(NODE_DESC);
                String videoURL = stepObj.getString(NODE_VIDEO_URL);
                String thumbnailURL = stepObj.getString(NODE_THUMBNAIL_URL);
                RecipeStep recipeStep = new RecipeStep(stepId, shortDesc, desc, videoURL, thumbnailURL);
                steps.add(recipeStep);
            }
            Recipe recipe = new Recipe(recipeId, recipeName, ingredients, steps);
            recipes.add(recipe);
        }

        return recipes;
    }

}

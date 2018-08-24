package com.example.dell.bakingapp.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.dell.bakingapp.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.IDN;
import java.sql.SQLTransactionRollbackException;
import java.util.ArrayList;
import java.util.List;
import com.example.dell.bakingapp.model.*;

public class JsonUtils {
    public static List<Recipe> fetchJsonRecipeResponse(String jsonRecipe) {
//        final String MOVIE_ROOT = "results";
        final String RECIPE_ID = "id";
        final String RECIPE_NAME = "name";
        final String RECIPE_SERVINGS = "servings";
        final String RECIPE_STEPS = "steps";
        final String RECIPE_INGREDIENTS = "ingredients";

        if (TextUtils.isEmpty(jsonRecipe)) {
            return null;
        }

        List<Recipe> recipes = new ArrayList<Recipe>();
        List<Steps> steps = new ArrayList<Steps>();
        List<Ingredients> ingredients = new ArrayList<Ingredients>();

        try {
//            JSONObject root = new JSONObject(jsonMovie);
            Log.d("Loader: ", jsonRecipe);
            JSONArray recipeResult = new JSONArray(jsonRecipe);

            for (int i = 0; i < recipeResult.length(); i++) {
                JSONObject recipeObject = recipeResult.getJSONObject(i);
                int recipeId = recipeObject.optInt(RECIPE_ID);
                String recipeName = recipeObject.optString(RECIPE_NAME);
                int recipeServing = recipeObject.optInt(RECIPE_SERVINGS);
                JSONArray recipeSteps = recipeObject.getJSONArray(RECIPE_STEPS);
                JSONArray recipeIngredients = recipeObject.getJSONArray(RECIPE_INGREDIENTS);
                steps = getRecipeSteps(recipeSteps);
                ingredients = getRecipeIngredients(recipeIngredients);

                recipes.add(new Recipe(recipeId, recipeName, recipeServing,steps,ingredients));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }


    private static List<Ingredients> getRecipeIngredients(JSONArray ingredients) {
        final String INGREDIENT_QTY = "quantity";
        final String INGREDIENT_MEASURE = "measure";
        final String INGREDIENT = "ingredient";
        String ing, ing_measure;
        int ing_qty;

        List<Ingredients> ingredientsList = new ArrayList<Ingredients>();

        for (int i = 0; i < ingredients.length(); i++) {
            try {
                JSONObject ingredientsObject = ingredients.getJSONObject(i);
                ing_qty = ingredientsObject.optInt(INGREDIENT_QTY);
                ing_measure = ingredientsObject.optString(INGREDIENT_MEASURE);
                ing = ingredientsObject.optString(INGREDIENT);
                ingredientsList.add(new Ingredients(ing_qty, ing_measure, ing));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return ingredientsList;
    }

    private static List<Steps> getRecipeSteps(JSONArray steps) {
        final String STEPS_ID = "id";
        final String STEPS_SHORT_DESCRIPTION = "shortDescription";
        final String STEPS_DESCRIPTION = "description";
        final String STEPS_VIDEO_URL = "videoURL";
        final String STEPS_THUMBNAIL_URL = "thumbnailURL";
        String steps_short, steps_desc, steps_video, steps_thumbnail;
        int steps_id;

        List<Steps> stepsList = new ArrayList<Steps>();

        for (int i = 0; i < steps.length(); i++) {
            try {
                JSONObject stepsObject = steps.getJSONObject(i);
                steps_id = stepsObject.optInt(STEPS_ID);
                steps_short = stepsObject.optString(STEPS_SHORT_DESCRIPTION);
                steps_desc = stepsObject.optString(STEPS_DESCRIPTION);
                steps_video = stepsObject.optString(STEPS_VIDEO_URL);
                steps_thumbnail = stepsObject.optString(STEPS_THUMBNAIL_URL);
                stepsList.add(new Steps(steps_id, steps_short, steps_desc, steps_video, steps_thumbnail));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return stepsList;
    }
}
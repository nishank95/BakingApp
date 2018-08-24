package com.example.dell.bakingapp.model;

import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable {

    private int mId;
    private String mRecipeName;
    private int mServings;
    private List<Ingredients> mIngredients;
    private List<Steps> mSteps;

    public Recipe(int id, String recipe_name, int servings,List<Steps> steps,List<Ingredients> ingredients){
        mId=id;
        mRecipeName=recipe_name;
        mServings=servings;
        mSteps = steps;
        mIngredients = ingredients;
    }

    public int getmId() {
        return mId;
    }

    public int getmServings() {
        return mServings;
    }

    public List<Steps> getmSteps() {
        return mSteps;
    }

    public List<Ingredients> getmIngredients() {
        return mIngredients;
    }

    public String getmRecipeName() {
        return mRecipeName;
    }

}

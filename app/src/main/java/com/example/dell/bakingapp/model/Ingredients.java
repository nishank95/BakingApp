package com.example.dell.bakingapp.model;

import java.io.Serializable;

public class Ingredients implements Serializable{
    private int mQuantity;
    private String mMeasure;
    private String mIngredientName;

    public Ingredients(){

    }
    public Ingredients(int quantity, String measure, String ingredient_name){
        mQuantity = quantity;
        mMeasure = measure;
        mIngredientName =ingredient_name;
    }

    public int getmQuantity() {
        return mQuantity;
    }

    public String getmMeasure() {
        return mMeasure;
    }

    public String getmIngredientName() {
        return mIngredientName;
    }
}

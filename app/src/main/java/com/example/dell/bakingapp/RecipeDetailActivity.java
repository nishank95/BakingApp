package com.example.dell.bakingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.example.dell.bakingapp.fragments.RecipeIngredientFragment;
import com.example.dell.bakingapp.fragments.RecipeStepsFragment;
import com.example.dell.bakingapp.fragments.StepsDetailFragment;
import com.example.dell.bakingapp.model.Ingredients;
import com.example.dell.bakingapp.model.Recipe;
import com.example.dell.bakingapp.model.Steps;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeStepsFragment.OnStepItemClickListener{

    @BindView(R.id.recipe_image_poster) ImageView recipeImagePoster;
    @BindView(R.id.toolbar_recipe_detail) Toolbar toolbar;
    @BindView(R.id.ctl_recipe_detail) CollapsingToolbarLayout collapsingToolbarLayout;
    static boolean mTwoPane;
    List<Ingredients> mIngredientList;
    List<Steps> mStepsList;
    Recipe mRecipeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        mRecipeModel = (Recipe) getIntent().getSerializableExtra("recipe");
        int recipe_no = getIntent().getIntExtra("clicked_pos",0);

        toolbar = findViewById(R.id.toolbar_recipe_detail);
        setSupportActionBar(toolbar);


        //Setting collapsing toolbar layout title.
        if (getIntent().getExtras() != null) {
            collapsingToolbarLayout.setTitle(mRecipeModel.getmRecipeName());
        }

        //Customizing the Collapsing Toolbar appearance.
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this,R.color.white));


        //Setting the up button for the toolbar.
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        switch (recipe_no){
            case 0: recipeImagePoster.setImageResource(R.drawable.i1);
                break;
            case 1: recipeImagePoster.setImageResource(R.drawable.i2);
                break;
            case 2: recipeImagePoster.setImageResource(R.drawable.i3);
                break;
            case 3: recipeImagePoster.setImageResource(R.drawable.i4);
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipeIngredientFragment ingredientFragment = new RecipeIngredientFragment();
        RecipeStepsFragment stepsFragment = new RecipeStepsFragment();

        if(savedInstanceState == null){
            if (mRecipeModel != null) {
                mIngredientList = mRecipeModel.getmIngredients();
                mStepsList = mRecipeModel.getmSteps();

                ingredientFragment.setIngredientModel(mIngredientList);
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_ingredient_container,ingredientFragment)
                        .commit();

                stepsFragment.setStepsModel(mStepsList);
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_steps_container,stepsFragment)
                        .commit();
            }
        }

        if(savedInstanceState != null){
            mRecipeModel = (Recipe) savedInstanceState.getSerializable("recipe_save");
            if (mRecipeModel != null ) {
                Log.d("onSaveInstance","in the saved instance");
                mIngredientList = mRecipeModel.getmIngredients();
                if(mIngredientList != null)
                    Log.d("onSaveInstance","ing ok");
                mStepsList = mRecipeModel.getmSteps();

                ingredientFragment.setIngredientModel(mIngredientList);
                fragmentManager.beginTransaction()
                        .replace(R.id.recipe_ingredient_container,ingredientFragment)
                        .commit();

                stepsFragment.setStepsModel(mStepsList);
                fragmentManager.beginTransaction()
                        .replace(R.id.recipe_steps_container,stepsFragment)
                        .commit();

            }
        }



        if(findViewById(R.id.twoPaneLayout) != null){
            mTwoPane = true;
            StepsDetailFragment stepDetailFragment = new StepsDetailFragment();
            stepDetailFragment.setStepsModel(mStepsList,0);
            Log.d("TAB","In two pane layout");
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_container, stepDetailFragment)
                    .commit();
        }
        else {
            mTwoPane = false;
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("recipe_save",mRecipeModel);
    }

    @Override
    public void onStepItemClicked(List<Steps> stepList,int position) {
        if(mTwoPane){
                StepsDetailFragment stepDetailFragment = new StepsDetailFragment();
                stepDetailFragment.setStepsModel(mStepsList,position);
                Log.d("TAB","In two pane layout");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_detail_container, stepDetailFragment)
                        .commit();
            }
        else {
                Intent intent = new Intent(getBaseContext(), StepDetailActivity.class);
                Steps step = stepList.get(position);
                Log.d("Steps:  ", step.getmShortDescription());
                intent.putExtra("step", (Serializable)stepList);
                intent.putExtra("step_no", position);
                startActivity(intent);
            }
    }
}



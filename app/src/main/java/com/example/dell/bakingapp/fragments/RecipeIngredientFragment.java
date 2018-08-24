package com.example.dell.bakingapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.bakingapp.R;
import com.example.dell.bakingapp.RecipeDetailActivity;
import com.example.dell.bakingapp.adapter.IngredientListAdapter;
import com.example.dell.bakingapp.adapter.StepListAdapter;
import com.example.dell.bakingapp.model.Ingredients;
import com.example.dell.bakingapp.model.Steps;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeIngredientFragment extends Fragment {

        @BindView(R.id.recipe_ingredient_list)RecyclerView ingredientList;
        private List<Ingredients> mIngredientModelList;


        @SuppressLint("ValidFragment")
        public RecipeIngredientFragment(){
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_recipe_ingredient,container,false);
            ButterKnife.bind(this,rootView);
            IngredientListAdapter mAdapter = new IngredientListAdapter(rootView.getContext(),mIngredientModelList);
            ingredientList.setLayoutManager(new LinearLayoutManager(rootView.getContext(),LinearLayoutManager.VERTICAL,false));
            ingredientList.setAdapter(mAdapter);
            return rootView;
        }


        public void setIngredientModel(List<Ingredients> ingredients){
            mIngredientModelList = ingredients;
        }

    }

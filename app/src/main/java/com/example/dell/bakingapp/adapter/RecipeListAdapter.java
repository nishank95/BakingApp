package com.example.dell.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.bakingapp.R;
import com.example.dell.bakingapp.RecipeDetailActivity;
import com.example.dell.bakingapp.StepDetailActivity;
import com.example.dell.bakingapp.model.Recipe;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder>{

    private Context mContext;
    private List<Recipe> mRecipe;
    private List<Integer> mImageId;

    public RecipeListAdapter(Context context, List<Recipe> recipe,List<Integer> imageId) {
        mContext=context;
        mRecipe=recipe;
        mImageId=imageId;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_recipe_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe data = mRecipe.get(position);

        holder.recipePoster.setImageResource(mImageId.get(position));
        holder.recipeName.setText(data.getmRecipeName());
        holder.recipeSteps.setText(data.getmSteps().size() + " Steps");
        holder.recipeServings.setText(data.getmServings() + " Servings");
        Log.d("Recipe:  ","Size= " + mImageId.size());
  }

    @Override
    public int getItemCount() {
        return mImageId.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_poster)ImageView recipePoster;
        @BindView(R.id.recipe_name)TextView recipeName;
        @BindView(R.id.recipe_steps)TextView recipeSteps;
        @BindView(R.id.recipe_servings)TextView recipeServings;

        ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick(R.id.recipe_card_item)
        void onclick(View view)
        {
//            Toast.makeText(mContext,"Clicked: " + getAdapterPosition(),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, RecipeDetailActivity.class);
            Recipe recipeModel = mRecipe.get(getAdapterPosition());
            intent.putExtra("recipe", recipeModel);
            intent.putExtra("clicked_pos", getAdapterPosition());
            mContext.startActivity(intent);

        }
    }
}

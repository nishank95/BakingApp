package com.example.dell.bakingapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.dell.bakingapp.R;
import com.example.dell.bakingapp.model.Ingredients;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


public class IngredientListAdapter  extends RecyclerView.Adapter<IngredientListAdapter.ViewHolder>{

    private Context mContext;
    private List<Ingredients> mRecipeIngredients;


    public IngredientListAdapter(Context context, List<Ingredients> ingredients) {
        mContext = context;
        mRecipeIngredients = ingredients;
        int size = mRecipeIngredients.size();
        Log.d("Ingredient: ", String.valueOf(size));
    }


    @NonNull
    @Override
    public IngredientListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ingredient_item_layout,parent,false);
        return new IngredientListAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredients ingredients = mRecipeIngredients.get(position);
        holder.ingredientQty.setText(Integer.toString(ingredients.getmQuantity()));
        holder.ingredientTitle.setText(ingredients.getmIngredientName());
        holder.ingredientMeasure.setText(ingredients.getmMeasure());
    }

    @Override
    public int getItemCount() {
        return mRecipeIngredients.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_title)TextView ingredientTitle;
        @BindView(R.id.ingredient_measure)TextView ingredientMeasure;
        @BindView(R.id.ingredient_quantity)TextView ingredientQty;

        ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

package com.example.dell.bakingapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.bakingapp.R;
import com.example.dell.bakingapp.adapter.RecipeListAdapter;
import com.example.dell.bakingapp.model.Ingredients;
import com.example.dell.bakingapp.model.Recipe;
import com.example.dell.bakingapp.utils.JsonUtils;
import com.example.dell.bakingapp.utils.NetworkUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;
import static android.media.CamcorderProfile.get;

public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Recipe>> {

    @BindView(R.id.recipe_list)RecyclerView rvRecipie;
    List<Integer> imageID = new ArrayList<Integer>();
    final String RECIPE_DATA_SOURCE = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    int RECIPE_LOADER_ID = 26;
    LoaderManager recipeLoader;
    RecipeListAdapter mAdapter;

    @SuppressLint("ValidFragment")
    public MainActivityFragment(){
    imageID.add(R.drawable.i1);
    imageID.add(R.drawable.i2);
    imageID.add(R.drawable.i3);
    imageID.add(R.drawable.i4);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_list,container,false);
        ButterKnife.bind(this,rootView);
        RecyclerView.LayoutManager mManager;
        if(isTablet(rootView.getContext())){
            int numberOfColumns = 2;
            mManager = new GridLayoutManager(rootView.getContext(), numberOfColumns);
            Log.d("TAB ", "In tablet mode");
        }
        else
            mManager = new LinearLayoutManager(rootView.getContext(),LinearLayoutManager.VERTICAL,false);

        rvRecipie.setLayoutManager(mManager);
        recipeLoader = getActivity().getSupportLoaderManager();
        recipeLoader.initLoader(RECIPE_LOADER_ID, null,this).forceLoad();
        return rootView;
    }


    @NonNull
    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, @Nullable Bundle args) {
        return new RecipeLoader(getContext(),RECIPE_DATA_SOURCE);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Recipe>> loader, List<Recipe> data) {
        int size = data.size();
        Log.d("Loader:",Integer.toString(size));

        pushDataToWidget(data);
        mAdapter = new RecipeListAdapter(getContext(),data,imageID);
        rvRecipie.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Recipe>> loader) {

    }



    private void pushDataToWidget(List<Recipe> data)
    {
        List<Ingredients> ingredients;
        String name,ing_name,quantity,measure;
        Recipe recipeData;
        StringBuilder ingredientString = new StringBuilder();
        StringBuilder ingredientQtyString = new StringBuilder();

        if(data != null){
            recipeData = data.get(0);
            ingredients = recipeData.getmIngredients();
            name = recipeData.getmRecipeName();
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("RECIPE_PREF",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            for (int i=0;i<ingredients.size();i++){
                ing_name = ingredients.get(i).getmIngredientName();
                quantity = Integer.toString(ingredients.get(i).getmQuantity());
                measure = ingredients.get(i).getmMeasure();
                ingredientString.append(ing_name).append("\n");
                if(ing_name.length() > 25){
                    ingredientQtyString.append(quantity).append(measure).append("\n\n");
                }
                else{
                    ingredientQtyString.append(quantity).append(measure).append("\n");
                }
            }

            editor.putString("RECIPE_NAME",name);
            editor.putString("RECIPE_INGREDIENTS",ingredientString.toString());
            editor.putString("RECIPE_INGREDIENTS_QTY",ingredientQtyString.toString());
            editor.apply();
        }

    }

    private static class RecipeLoader extends AsyncTaskLoader<List<Recipe>> {
        URL mUrl;
        RecipeLoader(Context context, String url) {
            super(context);
            try{
                mUrl = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        @Nullable
        @Override
        public List<Recipe> loadInBackground() {
            try {

                String jsonResponse = NetworkUtils.httpConnectionResponse(mUrl);
                return JsonUtils.fetchJsonRecipeResponse(jsonResponse);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    //Fetched from Stack Overflow
    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }


}

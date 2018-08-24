package com.example.dell.bakingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.dell.bakingapp.fragments.MainActivityFragment;
import com.example.dell.bakingapp.fragments.RecipeIngredientFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.internet_error_layout)FrameLayout errorLayout;
    @BindView(R.id.recipeListContainer)FrameLayout recipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if(checkInternetConnectivity()) {
            errorLayout.setVisibility(View.GONE);
            recipeContainer.setVisibility(View.VISIBLE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            MainActivityFragment mainFragment = new MainActivityFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.recipeListContainer, mainFragment)
                    .commit();

        }
        else{
            recipeContainer.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }

    }

    public boolean checkInternetConnectivity(){
        //Check internet connection//
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }



}

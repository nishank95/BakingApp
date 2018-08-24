package com.example.dell.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.dell.bakingapp.fragments.StepsDetailFragment;
import com.example.dell.bakingapp.model.Steps;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class StepDetailActivity extends AppCompatActivity {

    @BindView(R.id.next_step_btn)Button nextStepBtn;
    private int step_no;
    private StepsDetailFragment stepDetailFragment;
    private List<Steps> stepsModel;
    private int stepsModelSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        stepsModel = (List<Steps>) getIntent().getSerializableExtra("step");

        stepsModelSize = stepsModel.size();
        step_no = getIntent().getIntExtra("step_no",0);
            stepDetailFragment = new StepsDetailFragment();
        stepDetailFragment.setStepsModel(stepsModel,step_no);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_container, stepDetailFragment)
                    .commit();


    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportActionBar().hide();
        } else {
            getSupportActionBar().show();
        }
    }


    @OnClick(R.id.next_step_btn)
    void onClick(View view){

            StepsDetailFragment nextStepDetailFragment = new StepsDetailFragment();
            nextStepDetailFragment.setStepsModel(stepsModel,++step_no);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_container, nextStepDetailFragment)
                    .commit();
    }


}





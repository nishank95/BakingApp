package com.example.dell.bakingapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.example.dell.bakingapp.adapter.StepListAdapter;
import com.example.dell.bakingapp.model.Steps;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepsFragment extends Fragment implements StepListAdapter.StepItemClickListener{

    @BindView(R.id.recipe_step_list)RecyclerView stepList;
    private List<Steps> mStepsModelList;
    private OnStepItemClickListener clickListener;

    public interface OnStepItemClickListener{
        void onStepItemClicked(List<Steps> stepModel,int position);
    }

    @SuppressLint("ValidFragment")
    public RecipeStepsFragment(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            clickListener = (OnStepItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepItemClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_steps,container,false);
        ButterKnife.bind(this,rootView);
        StepListAdapter mAdapter = new StepListAdapter(this,mStepsModelList);
        stepList.setLayoutManager(new LinearLayoutManager(rootView.getContext(),LinearLayoutManager.VERTICAL,false));
        stepList.setAdapter(mAdapter);
        return rootView;
    }


    public void setStepsModel(List<Steps> steps){
    mStepsModelList = steps;
    }

    @Override
    public void onClickStep(List<Steps> stepModel,int position) {
        clickListener.onStepItemClicked(stepModel,position);
    }
}

package com.example.dell.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.bakingapp.R;
import com.example.dell.bakingapp.RecipeDetailActivity;
import com.example.dell.bakingapp.StepDetailActivity;
import com.example.dell.bakingapp.model.Ingredients;
import com.example.dell.bakingapp.model.Recipe;
import com.example.dell.bakingapp.model.Steps;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.ViewHolder>{

    private Context mContext;
    private List<Steps> mRecipeSteps;
    private StepItemClickListener itemClickListener;

    public interface StepItemClickListener {
        void onClickStep(List<Steps> stepModel,int position);
    }

    public StepListAdapter(StepItemClickListener stepItemClickListener, List<Steps> steps) {
        itemClickListener = stepItemClickListener;
        mRecipeSteps = steps;
    }



    @NonNull
    @Override
    public StepListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item_layout,parent,false);
        return new StepListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Steps steps = mRecipeSteps.get(position);
        holder.stepShortDescription.setText(steps.getmShortDescription());
        holder.stepNo.setText(Integer.toString(steps.getmId()));

    }

    @Override
    public int getItemCount() {
        return mRecipeSteps.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        @BindView(R.id.step_short_description)TextView stepShortDescription;
        @BindView(R.id.step_no)TextView stepNo;

        ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @OnClick(R.id.step_card_item)
        public void onClick(View view){
            int position = getAdapterPosition();
            Log.d("DEMO:" ,"Clicked : " + position);
            itemClickListener.onClickStep(mRecipeSteps,position);

//            RecipeDetailActivity detail = new RecipeDetailActivity();
//            Intent intent = new Intent(mContext, StepDetailActivity.class);
//            Steps step = mRecipeSteps.get(getAdapterPosition());
//            Log.d("Steps:  ", step.getmShortDescription());
//            intent.putExtra("step", (Serializable) mRecipeSteps);
//            intent.putExtra("step_no", getAdapterPosition());
//            mContext.startActivity(intent);

        }
    }
}



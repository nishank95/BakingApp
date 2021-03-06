package com.example.dell.bakingapp.fragments;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.bakingapp.R;
import com.example.dell.bakingapp.model.Ingredients;
import com.example.dell.bakingapp.model.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsDetailFragment extends Fragment {

    private List<Steps> mStepsList;
    private Steps mStep;
    private int mStepNo;
    @BindView(R.id.step_detail_description)TextView stepDescription;
    @BindView(R.id.step_no_label)TextView stepNo;
    @BindView(R.id.step_video_exo_player)SimpleExoPlayerView stepVideoPlayerView;
    @BindView(R.id.no_video_poster) ImageView noVideoPoster;
    private String PLAYER_POS ="PLAYER POSITION";
    private String PLAYER_STATE ="PLAYER STATE";

    private SimpleExoPlayer mExoPlayer;

    public StepsDetailFragment(){
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail,container,false);
        ButterKnife.bind(this,rootView);
        String step_label= "";
        String videoUrl="";


        if(savedInstanceState == null){
            mStep = mStepsList.get(mStepNo);
        }

       if(savedInstanceState != null){
           if(mStepsList == null){
               Log.d("Saved Instance","Set steps model");
               mStep = (Steps) savedInstanceState.getSerializable("recipe_save");
           }
        }

        stepDescription.setText(mStep.getmDescription());
        step_label = "Step " + mStep.getmId();
        stepNo.setText(step_label);
        videoUrl = mStep.getmVideoUrl();
        if(!videoUrl.equals("")){
            noVideoPoster.setVisibility(View.GONE);
            stepVideoPlayerView.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(videoUrl));
            Log.d("Cycle","In ONCREATE VIEW");
            if(savedInstanceState != null){
                mExoPlayer.seekTo(savedInstanceState.getLong(PLAYER_POS));
                mExoPlayer.setPlayWhenReady(savedInstanceState.getBoolean(PLAYER_STATE));
            }
        }
        else {
            stepVideoPlayerView.setVisibility(View.GONE);
            noVideoPoster.setVisibility(View.VISIBLE);
            }


        return rootView;
    }


    public void setStepsModel(List<Steps> steps, int stepNo){
        mStepsList = steps;
        if(stepNo == steps.size())
        {

        }
        else {
            mStepNo = stepNo;
        }

    }

    private void initializePlayer(Uri mediaUri){
        if(mExoPlayer == null){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(),trackSelector,loadControl);
            stepVideoPlayerView.setPlayer(mExoPlayer);
            String userAgent = Util.getUserAgent(getActivity(),"Baking App");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri,new DefaultDataSourceFactory(getActivity(),userAgent)
                    ,new DefaultExtractorsFactory(),null,null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);

        }

    }

    private void releasePlayer(){
        if(!mStep.getmVideoUrl().equals("") && mExoPlayer != null){
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer= null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("recipe_save", mStep);
        outState.putLong(PLAYER_POS, mExoPlayer.getCurrentPosition());
        outState.putBoolean(PLAYER_STATE, mExoPlayer.getPlayWhenReady());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Cycle","ONSTART");
        initializePlayer(Uri.parse(mStep.getmVideoUrl()));
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("Cycle","ONRESUME");
        initializePlayer(Uri.parse(mStep.getmVideoUrl()));
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Cycle","ONSTOP");
        stepVideoPlayerView.setPlayer(null);
        if(mExoPlayer != null){
            mExoPlayer.setPlayWhenReady(false);
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Cycle","ONDESTROY");
        releasePlayer();
        stepVideoPlayerView.setPlayer(null);
    }
}

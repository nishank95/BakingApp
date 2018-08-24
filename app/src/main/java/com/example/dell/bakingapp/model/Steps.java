package com.example.dell.bakingapp.model;

import java.io.Serializable;

public class Steps implements Serializable {

    int mId;
    String mDescription;
    String mShortDescription;
    String mVideoUrl;
    String mThumbnailUrl;



    public Steps(){
    }

    public Steps(int id, String short_description, String description, String video_url,String thumbnail_url){
        mId = id;
        mShortDescription = short_description;
        mDescription = description;
        mVideoUrl = video_url;
        mThumbnailUrl = thumbnail_url;
    }

    public int getmId() {
        return mId;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmShortDescription() {
        return mShortDescription;
    }

    public String getmThumbnailUrl() {
        return mThumbnailUrl;
    }

    public String getmVideoUrl() {
        return mVideoUrl;
    }

}




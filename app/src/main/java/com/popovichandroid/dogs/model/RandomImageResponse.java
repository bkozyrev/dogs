package com.popovichandroid.dogs.model;

import com.google.gson.annotations.SerializedName;

public class RandomImageResponse {

    @SerializedName("status")
    private String mStatus;

    @SerializedName("message")
    private String mBreedData;

    public String getStatus() {
        return mStatus;
    }

    public String getBreedData() {
        return mBreedData;
    }
}

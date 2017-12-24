package com.popovichandroid.dogs.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BreedResponse {

    @SerializedName("status")
    private String mStatus;

    @SerializedName("message")
    private ArrayList<String> mBreedData;

    public String getStatus() {
        return mStatus;
    }

    public ArrayList<String> getBreedData() {
        return mBreedData;
    }
}
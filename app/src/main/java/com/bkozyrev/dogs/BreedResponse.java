package com.bkozyrev.dogs;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BreedResponse {

    @SerializedName("status")
    private String mStatus;

    @SerializedName("message")
    private ArrayList<String> mBreedNames;

    public String getStatus() {
        return mStatus;
    }

    public ArrayList<String> getBreedNames() {
        return mBreedNames;
    }
}
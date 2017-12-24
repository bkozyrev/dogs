package com.popovichandroid.dogs.model;

public class BreedItem {

    private String mBreedName;
    private String mBreedImageUrl;

    public BreedItem(String breedName, String breedImageUrl) {
        this.mBreedName = breedName;
        this.mBreedImageUrl = breedImageUrl;
    }

    public String getBreedName() {
        return mBreedName;
    }

    public String getBreedImageUrl() {
        return mBreedImageUrl;
    }

}

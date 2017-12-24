package com.bkozyrev.dogs.interfaces;

public interface OnImageResponseListener {

    void onImageResponseSuccess(String imageUrl);

    void onBreedResponseFail();
}

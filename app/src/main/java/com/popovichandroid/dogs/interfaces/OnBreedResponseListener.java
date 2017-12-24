package com.popovichandroid.dogs.interfaces;

import java.util.ArrayList;

public interface OnBreedResponseListener {

    void onBreedsResponseSuccess(ArrayList<String> breeds);

    void onBreedResponseFail();
}

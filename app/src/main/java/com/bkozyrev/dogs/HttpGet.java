package com.bkozyrev.dogs;

import com.bkozyrev.dogs.interfaces.OnBreedResponseListener;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpGet {

    private String allBreedsUrl = "https://dog.ceo/api/breeds/list";

    private final OkHttpClient client;
    private final Gson gson = new Gson();

    public HttpGet() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    public void getAllBreeds(final OnBreedResponseListener listener) {
        Request request = new Request.Builder()
                .url(allBreedsUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onBreedResponseFail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.onBreedsResponseSuccess(gson.fromJson(response.body().string(), BreedResponse.class).getBreedNames());
            }
        });
    }
}

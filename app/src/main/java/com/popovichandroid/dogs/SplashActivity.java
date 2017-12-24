package com.popovichandroid.dogs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.popovichandroid.dogs.interfaces.OnImageResponseListener;
import com.squareup.picasso.Picasso;

public class SplashActivity extends AppCompatActivity {

    private AppCompatImageView mBreedImage;
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private HttpGet getApi = new HttpGet();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mBreedImage = findViewById(R.id.breed_image);

        getApi.getAllBreedsRandomImage(new OnImageResponseListener() {
            @Override
            public void onImageResponseSuccess(final String imageUrl) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Picasso.with(SplashActivity.this).load(imageUrl).into(mBreedImage);
                    }
                });
            }

            @Override
            public void onBreedResponseFail() {

            }
        });

        findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

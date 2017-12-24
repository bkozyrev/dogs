package com.bkozyrev.dogs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.bkozyrev.dogs.adapters.AllBreedImagesAdapter;
import com.bkozyrev.dogs.interfaces.OnBreedResponseListener;
import com.bkozyrev.dogs.utils.DividerItemDecoration;

import java.util.ArrayList;

public class BreedImagesActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String BREED_NAME_EXTRA = "breed_name_extra";

    private RecyclerView mList;
    private AllBreedImagesAdapter mAdapter;
    private ProgressBar mProgressBar;
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    private HttpGet getApi = new HttpGet();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breed_images);

        mList = (RecyclerView) findViewById(R.id.list);
        mList.setLayoutManager(new GridLayoutManager(this, 3));
        mList.setAdapter(mAdapter = new AllBreedImagesAdapter(this, this));
        mList.addItemDecoration(new DividerItemDecoration(8, 3));
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);

        getApi.getAllBreedImages(getIntent().getStringExtra(BREED_NAME_EXTRA), new OnBreedResponseListener() {
            @Override
            public void onBreedsResponseSuccess(final ArrayList<String> breeds) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setData(breeds);
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onBreedResponseFail() {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.container_grid_image:
                Intent intent = new Intent(this, FullScreenImageActivity.class);
                intent.putExtra(FullScreenImageActivity.MEDIA_START_POS_EXTRA, mList.getChildAdapterPosition(view));
                intent.putStringArrayListExtra(FullScreenImageActivity.MEDIA_URL_EXTRA, mAdapter.getData());
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
package com.popovichandroid.dogs;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.popovichandroid.dogs.adapters.AllBreedsListAdapter;
import com.popovichandroid.dogs.interfaces.OnBreedResponseListener;
import com.popovichandroid.dogs.interfaces.OnImageResponseListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mList;
    private SwipeRefreshLayout mSwipeRefresh;
    private AllBreedsListAdapter mAdapter;
    private ProgressBar mProgressBar;
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private HttpGet getApi = new HttpGet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setOnRefreshListener(this);
        mList = (RecyclerView) findViewById(R.id.list);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.setAdapter(mAdapter = new AllBreedsListAdapter(this, this));
        mProgressBar = findViewById(R.id.progress_bar);

        requestData();
    }

    private void requestData() {
        mProgressBar.setVisibility(View.VISIBLE);
        getApi.getAllBreeds(new OnBreedResponseListener() {
            @Override
            public void onBreedsResponseSuccess(final ArrayList<String> breeds) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setVisibility(View.GONE);
                        mSwipeRefresh.setRefreshing(false);
                    }
                });

                for (final String breedName: breeds) {
                    getApi.getBreedRandomImage(breedName, new OnImageResponseListener() {
                        @Override
                        public void onImageResponseSuccess(final String imageUrl) {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.updateItem(breedName, imageUrl, breeds.indexOf(breedName));
                                }
                            });
                        }

                        @Override
                        public void onBreedResponseFail() {

                        }
                    });
                }
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query) {
                mAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });

        return true;
    }

    @Override
    public void onRefresh() {
        mAdapter.clearData();
        requestData();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, BreedImagesActivity.class);
        intent.putExtra(BreedImagesActivity.BREED_NAME_EXTRA,
                mAdapter.getItem(mList.getChildAdapterPosition((View) view.getParent().getParent())).getBreedName());
        startActivity(intent);
    }
}

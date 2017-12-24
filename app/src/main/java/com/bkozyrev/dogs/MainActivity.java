package com.bkozyrev.dogs;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bkozyrev.dogs.interfaces.OnBreedResponseListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mList;
    private SwipeRefreshLayout mSwipeRefresh;
    private AllBreedsListAdapter mAdapter;

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

        getApi.getAllBreeds(new OnBreedResponseListener() {
            @Override
            public void onBreedsResponseSuccess(final ArrayList<String> breeds) {
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.addData(breeds);
                    }
                });
            }

            @Override
            public void onBreedResponseFail() {

            }
        });
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View v) {

    }
}

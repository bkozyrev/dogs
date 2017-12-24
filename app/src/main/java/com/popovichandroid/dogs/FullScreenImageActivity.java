package com.popovichandroid.dogs;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.popovichandroid.dogs.adapters.FullScreenImageAdapter;

import java.util.ArrayList;

public class FullScreenImageActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String MEDIA_URL_EXTRA = "images_url";
    public static final String MEDIA_START_POS_EXTRA = "images_start_pos";

    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private RelativeLayout mContainer;
    private ArrayList<String> mUrls;
    private int mTotalItemsCount, mStartPos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_images);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mContainer = (RelativeLayout) findViewById(R.id.container_full_screen_activity);

        mUrls = new ArrayList<>();
        mStartPos = 0;
        Intent intent = getIntent();
        if (intent != null) {
            mUrls = intent.getStringArrayListExtra(MEDIA_URL_EXTRA);
            mStartPos = intent.getIntExtra(MEDIA_START_POS_EXTRA, 0);
        }
        mTotalItemsCount = mUrls.size();

        setupViewPager();
        setupToolbar();
    }

    private void setupToolbar() {
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.black));
        }
    }

    private void setupViewPager() {
        mViewPager.setAdapter(new FullScreenImageAdapter(this, mUrls, this));
        mViewPager.setPageMargin(16);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mToolbar.setTitle(getString(R.string.toolbar_images_count, position + 1, mTotalItemsCount));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setCurrentItem(mStartPos);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.photo_view:
                if (mToolbar.getVisibility() == View.VISIBLE) {
                    mToolbar.setVisibility(View.GONE);
                } else {
                    mToolbar.setVisibility(View.VISIBLE);
                }
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

package com.popovichandroid.dogs.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.popovichandroid.dogs.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FullScreenImageAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private View.OnClickListener mListener;
    private ArrayList<String> mUrls;

    public FullScreenImageAdapter(Context context, ArrayList<String> urls, View.OnClickListener listener) {
        this.mUrls = urls;
        this.mContext = context;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mListener = listener;
    }

    @Override
    public int getCount() {
        return mUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View viewLayout = mInflater.inflate(R.layout.item_full_screen_image, container, false);

        PhotoView photoView = (PhotoView) viewLayout.findViewById(R.id.photo_view);

        /*Picasso.with(mContext).load(mUrls.get(position)).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                photoView.setImageBitmap(resource);
            }
        });*/

        Picasso.with(mContext).load(mUrls.get(position)).into(photoView);

        photoView.setOnClickListener(mListener);

        container.addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}

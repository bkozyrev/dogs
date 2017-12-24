package com.popovichandroid.dogs.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.popovichandroid.dogs.R;
import com.popovichandroid.dogs.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllBreedImagesAdapter extends RecyclerView.Adapter<AllBreedImagesAdapter.BreedImageViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private View.OnClickListener mListener;
    private ArrayList<String> mData;
    private int mItemWidth;

    public AllBreedImagesAdapter(Context context, View.OnClickListener listener) {
        this.mContext = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mListener = listener;
        mData = new ArrayList<>();
        mItemWidth = Utils.getScreenWidth(context) / 3;
    }

    @Override
    public BreedImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item_breed_grid, parent, false);
        itemView.getLayoutParams().width = mItemWidth;
        itemView.getLayoutParams().height = mItemWidth;
        return new BreedImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BreedImageViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(ArrayList<String> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public ArrayList<String> getData() {
        return mData;
    }

    public class BreedImageViewHolder extends RecyclerView.ViewHolder {

        private AppCompatImageView ivImage;

        public BreedImageViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(mListener);
            ivImage = (AppCompatImageView) itemView.findViewById(R.id.breed_image);
        }

        public void bind(String url) {
            Picasso.with(mContext).load(url).into(ivImage);
        }
    }
}
package com.bkozyrev.dogs.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bkozyrev.dogs.R;

import java.util.ArrayList;
import java.util.List;

public class AllBreedsListAdapter extends RecyclerView.Adapter<AllBreedsListAdapter.BreedViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mData, mDataOrigin;
    private View.OnClickListener mClickListener;

    public AllBreedsListAdapter(Context context, View.OnClickListener clickListener) {
        this.mContext = context;
        this.mClickListener = clickListener;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mData = new ArrayList<>();
        mDataOrigin = new ArrayList<>();
    }

    @Override
    public BreedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BreedViewHolder(mInflater.inflate(R.layout.list_item_breed, parent, false));
    }

    @Override
    public void onBindViewHolder(BreedViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void clearData() {
        mData.clear();
        mDataOrigin.clear();
        notifyDataSetChanged();
    }

    public void addData(List<String> data) {
        mData.addAll(data);
        mDataOrigin.addAll(data);
        notifyItemRangeInserted(mData.size() - data.size(), data.size());
    }

    public String getBreedName(int pos) {
        return mData.get(pos);
    }

    public String getItem(int pos) {
        return pos < mData.size() ? mData.get(pos) : null;
    }

    public void filter(String query) {
        mData.clear();
        if (TextUtils.isEmpty(query)) {
            mData.addAll(mDataOrigin);
        } else {
            query = query.toLowerCase();
            for (String breedName : mDataOrigin) {
                if (breedName.toLowerCase().startsWith(query)) {
                    mData.add(breedName);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class BreedViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView tvName, tvLikesCount;
        private AppCompatImageView ivBreed, ivLike;
        private LinearLayout likeContainer;

        public BreedViewHolder(View itemView) {
            super(itemView);
            tvName = (AppCompatTextView) itemView.findViewById(R.id.breed_name);
            ivBreed = (AppCompatImageView) itemView.findViewById(R.id.breed_image);
            ivLike = (AppCompatImageView) itemView.findViewById(R.id.like_image);
            tvLikesCount = (AppCompatTextView) itemView.findViewById(R.id.likes_count);
            likeContainer = (LinearLayout) itemView.findViewById(R.id.like_container);
            ivBreed.setOnClickListener(mClickListener);
            likeContainer.setOnClickListener(mClickListener);
        }

        public void bind(String breedName) {
            String upperString = breedName.substring(0, 1).toUpperCase() + breedName.substring(1);
            tvName.setText(upperString);
            //Glide.with(mContext).load(breedResponse.getBreedImageUrl()).into(ivBreed);
        }
    }
}
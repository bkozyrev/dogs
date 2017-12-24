package com.popovichandroid.dogs.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.popovichandroid.dogs.R;
import com.popovichandroid.dogs.model.BreedItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AllBreedsListAdapter extends RecyclerView.Adapter<AllBreedsListAdapter.BreedViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<BreedItem> mData, mDataOrigin;
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

    public void updateItem(String breedName, String imageUrl, int position) {
        mData.add(new BreedItem(breedName, imageUrl));
        mDataOrigin.add(new BreedItem(breedName, imageUrl));
        notifyItemChanged(position);
    }

    public BreedItem getBreedName(int pos) {
        return mData.get(pos);
    }

    public BreedItem getItem(int pos) {
        return pos < mData.size() ? mData.get(pos) : null;
    }

    public void filter(String query) {
        mData.clear();
        if (TextUtils.isEmpty(query)) {
            mData.addAll(mDataOrigin);
        } else {
            query = query.toLowerCase();
            for (BreedItem breedItem : mDataOrigin) {
                if (breedItem.getBreedName().toLowerCase().startsWith(query)) {
                    mData.add(breedItem);
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

        public void bind(BreedItem breedItem) {
            String upperString = breedItem.getBreedName().substring(0, 1).toUpperCase() + breedItem.getBreedName().substring(1);
            tvName.setText(upperString);
            Picasso.with(mContext).load(breedItem.getBreedImageUrl()).into(ivBreed);
        }
    }
}
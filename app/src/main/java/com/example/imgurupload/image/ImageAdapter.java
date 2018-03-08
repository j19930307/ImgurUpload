package com.example.imgurupload.image;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.imgurupload.*;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private ArrayList<Image.DataBean> mImages;
    private SparseBooleanArray mSelectedItemIds;
    private ImgurListener listener;

    public ImageAdapter(ArrayList<Image.DataBean> mImages, ImgurListener listener) {
        this.mImages = mImages;
        this.mSelectedItemIds = new SparseBooleanArray();
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        CheckBox checkBox;
        RelativeLayout item;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            mImageView = itemView.findViewById(R.id.image);
            checkBox = itemView.findViewById(R.id.isChecked);
        }
    }


    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_image_list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder holder, final int position) {

        if (mSelectedItemIds.size() == 0) {
            holder.checkBox.setVisibility(View.GONE);
        } else
            holder.checkBox.setVisibility(View.VISIBLE);

        final Image.DataBean image = mImages.get(position);
        if(image.getLink().toUpperCase().endsWith(".GIF")) {
            Glide.with(holder.mImageView.getContext())
                    .load(ImageUtils.resize(image.getLink(), ImageUtils.MEDIUM))
                    .asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).centerCrop()
                    .into(holder.mImageView);
        } else {
            Glide.with(holder.mImageView.getContext()).load(ImageUtils.resize(image.getLink(), ImageUtils.MEDIUM)).centerCrop().into(holder.mImageView);
        }

        //holder.itemView.setBackgroundColor(mSelectedItemIds.get(position) ? 0x9934B5E4 : Color.TRANSPARENT);
        holder.checkBox.setChecked(mSelectedItemIds.get(position) ? true : false);

        /*if (mSelectedItemIds.get(position)) {
            holder.mImageView.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        } else
            holder.mImageView.setColorFilter(Color.TRANSPARENT);
        */

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) {
                    listener.onPhotoClick(position);
                }
            }
        });

        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(listener != null) {
                    listener.onPhotoLongClick(position);
                }
                return true;
            }
        });
    }

    public void toggleSelection(int position) {
        if (!mSelectedItemIds.get(position))
            mSelectedItemIds.put(position, true);
        else
            mSelectedItemIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemIds.size();
    }

    public void clearSeletedItem() {
        mSelectedItemIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public SparseBooleanArray getSelectedItemIds() {
        return mSelectedItemIds;
    }

    public void addItems(ArrayList newItems) {
        mImages.addAll(newItems);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mImages.remove(position);
        notifyDataSetChanged();
    }
}

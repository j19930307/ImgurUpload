package com.example.imgurupload;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CreateAlbumAdapter extends RecyclerView.Adapter<CreateAlbumAdapter.ViewHolder> {

    private ArrayList<Uri> imageUri;

    public CreateAlbumAdapter(ArrayList<Uri> resource) {
        imageUri = resource;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.album_image_view);
        }
    }

    @Override
    public CreateAlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_album_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CreateAlbumAdapter.ViewHolder holder, int position) {
        Glide.with(holder.imageView.getContext()).load(imageUri.get(position)).centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageUri.size();
    }
}

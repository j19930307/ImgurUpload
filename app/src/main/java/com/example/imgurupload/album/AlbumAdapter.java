package com.example.imgurupload.album;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.imgurupload.ImageUtils;
import com.example.imgurupload.R;
import com.example.imgurupload.image.ImgurListener;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    public List<Album.DataBean> album = new ArrayList<>();
    private SparseBooleanArray mSelectedItemIds;
    private ImgurListener listener;

    public AlbumAdapter(List<Album.DataBean> album, ImgurListener listener) {
        this.album = album;
        this.mSelectedItemIds = new SparseBooleanArray();
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView url_text_view;
        private TextView title;
        private TextView count;
        private LinearLayout item;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.album_fragment_iv);
            title = itemView.findViewById(R.id.album_title);
            count = itemView.findViewById(R.id.album_count);
            item = itemView.findViewById(R.id.album_list_item);
        }
    }

    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.album_fragment_list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AlbumAdapter.ViewHolder holder, final int position) {
        Glide.with(holder.imageView.getContext())
                .load(ImageUtils.resize("https://i.imgur.com/" + album.get(position).getCover() + ".jpg", ImageUtils.MEDIUM))
                .centerCrop()
                .into(holder.imageView);
        holder.title.setText(album.get(position).getTitle());
        holder.count.setText(String.valueOf(album.get(position).getImages_count()) + "張相片");
        //holder.itemView.setBackgroundColor(mSelectedItemIds.get(position) ? 0x9934B5E4 : Color.TRANSPARENT);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPhotoClick(position);
            }
        });

        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onPhotoLongClick(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return album.size();
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

    public void remove(int selected) {
        album.remove(selected);
    }
}

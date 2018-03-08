package com.example.imgurupload.album;

import android.content.Context;

import com.example.imgurupload.api.ImgurApiService;
import com.example.imgurupload.api.RetrofitService;
import com.example.imgurupload.image.Image;

import retrofit2.Callback;

public class AlbumManager {
    private static AlbumManager instance;
    private Context context;

    public static AlbumManager getInstance(Context context) {
        if (instance == null) {
            instance = new AlbumManager(context);
        }
        return instance;
    }

    private AlbumManager(Context context) {
        this.context = context;
    }

    public void getAlbums(int page, Callback<Album> callback) {
        RetrofitService.getInstance(context).createApi(ImgurApiService.class).getAlbums("me", page).enqueue(callback);
    }
}


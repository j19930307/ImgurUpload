package com.example.imgurupload.image;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.imgurupload.api.ImgurApiService;
import com.example.imgurupload.api.RetrofitService;
import com.example.imgurupload.login.Account;
import com.example.imgurupload.login.AccountManager;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Jason on 2017/12/12.
 */

public class ImageManager {
    private static ImageManager instance;
    private Context context;

    public static ImageManager getInstance(Context context) {
        if (instance == null) {
            instance = new ImageManager(context);
        }
        return instance;
    }

    private ImageManager(Context context) {
        this.context = context;
    }

    public void getImages(int page, Callback<Image> callback) {
        RetrofitService.getInstance(context).createApi(ImgurApiService.class).getImages("me", page).enqueue(callback);
    }
}

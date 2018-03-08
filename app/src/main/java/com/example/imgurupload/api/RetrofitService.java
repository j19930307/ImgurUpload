package com.example.imgurupload.api;

import android.content.Context;
import android.util.Log;

import com.example.imgurupload.login.Account;
import com.example.imgurupload.login.AccountManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RetrofitService {

    private static final String API_URL = "https://api.imgur.com/3/";
    private static RetrofitService instance;
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;

    public static RetrofitService getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitService(context);
        }
        return instance;
    }

    public RetrofitService(Context context) {

        Log.d("token", "retrofit");

        //HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        //logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new RequestInterceptor())
                //.addInterceptor(logging)
                .build();

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API_URL)
                .client(okHttpClient)
                .build();
    }

    public <T> T createApi(Class<T> tClass) {
        return retrofit.create(tClass);
    }
}

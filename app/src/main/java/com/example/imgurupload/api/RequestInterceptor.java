package com.example.imgurupload.api;

import android.text.TextUtils;
import android.util.Log;

import com.example.imgurupload.MyApplication;
import com.example.imgurupload.login.Account;
import com.example.imgurupload.login.AccountManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Jason on 2017/12/11.
 */

public class RequestInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        String accessToken = AccountManager.getInstance(MyApplication.getInstance()).getAccessToken();
        Request request = chain.request()
                .newBuilder()
                .addHeader("Authorization", TextUtils.isEmpty(accessToken) ? "Client-ID 559663b09041d88" : "Bearer " + accessToken)
                .build();

        Log.d("header", request.headers().toString());
        Log.d("request", request.toString());

        return chain.proceed(request);
    }
}

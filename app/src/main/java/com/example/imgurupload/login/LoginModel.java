package com.example.imgurupload.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.imgurupload.MyApplication;
import com.example.imgurupload.api.ImgurApiService;
import com.example.imgurupload.api.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jason on 2017/12/11.
 */

public class LoginModel {
    private AccountManager accountManager;

    public LoginModel(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void saveToken(String refreshToken, String accessToken, long expiresIn) {
        accountManager.saveUserToken(refreshToken, accessToken, expiresIn);
    }

    public void saveAccountBase(Account account) {
        accountManager.saveAccountBase(account);
    }

    public void getAccountId(Callback<Account> callback) {
        RetrofitService.getInstance(MyApplication.getContext()).createApi(ImgurApiService.class).getAccountBase("me")
                .enqueue(callback);

    }
}

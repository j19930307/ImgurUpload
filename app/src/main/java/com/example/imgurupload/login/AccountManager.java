package com.example.imgurupload.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class AccountManager {

    private static final String AUTH = "authorization";
    private static final String REFRESH_TOEKN = "refreshToken";
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String EXPIRES_IN = "expiresIn";

    private static AccountManager instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    public static AccountManager getInstance(Context context) {
        if(instance == null) {
            instance = new AccountManager(context);
        }
        return instance;
    }

    private AccountManager(Context context) {
        sharedPreferences = context.getSharedPreferences(AUTH, MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(sharedPreferences.getString(ACCESS_TOKEN, ""));
    }

    public void saveUserToken(String refreshToken, String accessToken, long expiresIn) {
        sharedPreferencesEditor
                .putString(REFRESH_TOEKN, refreshToken)
                .putString(ACCESS_TOKEN, accessToken)
                .putLong(EXPIRES_IN, expiresIn)
                .commit();
    }

    public String getAccessToken() {
        return sharedPreferences.getString(ACCESS_TOKEN, "");
    }

    public void saveAccountBase(Account account) {
        Gson gson = new Gson();
        sharedPreferencesEditor
                .putString("account", gson.toJson(account))
                .commit();
    }

    public String getAccountName() {
        Gson gson = new Gson();
        Account account = gson.fromJson(sharedPreferences.getString("account", ""), Account.class);
        if(account != null) {
            return account.getData().getUrl();
        } else {
            return "";
        }

    }

    public void clearCache() {
        Log.v("sharfToken", String.valueOf(sharedPreferences.getString(ACCESS_TOKEN, "").length()));
        sharedPreferencesEditor.clear().commit();
        Log.v("sharfToken",  String.valueOf(sharedPreferences.getString(ACCESS_TOKEN, "").length()));
    }
}

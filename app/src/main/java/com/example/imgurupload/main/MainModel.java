package com.example.imgurupload.main;

import android.content.Context;
import android.text.TextUtils;

import com.example.imgurupload.MyApplication;
import com.example.imgurupload.login.AccountManager;

/**
 * Created by Jason on 2017/12/7.
 */

class MainModel {
    public void saveUserToken() {
    }

    public boolean checkUserStatus(Context context) {
        return TextUtils.isEmpty(AccountManager.getInstance(context).getAccessToken());
    }
}

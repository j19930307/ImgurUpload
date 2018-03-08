package com.example.imgurupload.main;

import android.content.Context;

/**
 * Created by Jason on 2017/12/6.
 */

public interface MainContract {
    interface View {
        void showNotLogin();
        void loadPhotos();
        void loadAlbums();
        void loadAlbumImage(String id);
        void popUpLoginPage();
        void navigateToMainActivity();
        void loginFailed();
    }

    interface Presenter {
        void showFragment(Context context);
        void clickToLogin();
    }
}

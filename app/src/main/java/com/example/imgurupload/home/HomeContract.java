package com.example.imgurupload.home;

import android.content.Context;

/**
 * Created by Jason on 2017/12/6.
 */

public interface HomeContract {
    interface View {
        void showNotLogin();
        void loadPhotos();
        void loadAlbums();
        void popUpLoginPage();
        void navigateToMainActivity();
        void loginFailed();
    }

    interface Presenter {
        void showFragment(Context context);
        void clickToLogin();
    }
}

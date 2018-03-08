package com.example.imgurupload.main;

import android.content.Context;

public class MainPresenter implements MainContract.Presenter {
    private final MainContract.View mainView;
    private final MainModel mainModel;


    public MainPresenter(MainContract.View mainView, MainModel mainModel) {
        this.mainView = mainView;
        this.mainModel = mainModel;
    }

    @Override
    public void showFragment(Context context) {
        if(!mainModel.checkUserStatus(context)) {
            mainView.loadPhotos();
        } else {
            mainView.showNotLogin();
        }
    }

    @Override
    public void clickToLogin() {
        mainView.popUpLoginPage();
        mainModel.saveUserToken();
    }
}

package com.example.imgurupload.home;

import android.content.Context;

public class HomePresenter implements HomeContract.Presenter {
    private final HomeContract.View homeView;
    private final HomeModel homeModel;


    public HomePresenter(HomeContract.View mainView, HomeModel homeModel) {
        this.homeView = mainView;
        this.homeModel = homeModel;
    }

    @Override
    public void showFragment(Context context) {
        if(!homeModel.checkUserStatus(context)) {
            homeView.loadPhotos();
        } else {
            homeView.showNotLogin();
        }
    }

    @Override
    public void clickToLogin() {
        homeView.popUpLoginPage();
        homeModel.saveUserToken();
    }
}

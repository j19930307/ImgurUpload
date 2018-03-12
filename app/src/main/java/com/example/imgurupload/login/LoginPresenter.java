package com.example.imgurupload.login;

import android.content.Intent;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jason on 2017/12/11.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginModel loginModel;
    private LoginContract.View loginView;

    public LoginPresenter(LoginModel loginModel, LoginContract.View loginView) {
        this.loginModel = loginModel;
        this.loginView = loginView;
    }

    @Override
    public void userLogin() {
        loginView.openLoginPage();
    }

    @Override
    public void saveToken(String refreshToken, String accessToken, long expiresIn) {
        loginModel.saveToken(refreshToken, accessToken, expiresIn);
        loginModel.getAccountId(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                        //Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        loginModel.saveAccountBase(response.body());
                        loginView.navigateToMainActivity();
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                loginView.loginFail();
            }
        });
    }
}

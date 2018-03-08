package com.example.imgurupload.login;

/**
 * Created by Jason on 2017/12/11.
 */

public interface LoginContract {
    interface View {
        void openLoginPage();
        void loginFail();
        void navigateToMainActivity();
    }
    interface Presenter {
        void userLogin();
        void saveToken(String refreshToken, String accessToken, long expiresIn);
    }
}

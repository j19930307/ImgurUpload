package com.example.imgurupload.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.imgurupload.ActivityUtils;
import com.example.imgurupload.R;
import com.example.imgurupload.login.AccountManager;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button login;
    AccountManager accountManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        accountManager = AccountManager.getInstance(this);
        if(accountManager.isLogin()) {
            navigationToMainActivity();
        }
        login = findViewById(R.id.btn_login);
        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(LoginActivity.this, com.example.imgurupload.login.LoginActivity.class));
    }

    public void navigationToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }
}

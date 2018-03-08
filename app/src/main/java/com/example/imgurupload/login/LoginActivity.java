package com.example.imgurupload.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.imgurupload.R;
import com.example.imgurupload.api.ImgurApiService;
import com.example.imgurupload.api.RetrofitService;
import com.example.imgurupload.main.MainActivity;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    WebView loginWebView;

    private static final String REDIRECT_URL = "https://imgur.com";

    private static final Pattern accessTokenPattern = Pattern.compile("access_token=([^&]*)");
    private static final Pattern refreshTokenPattern = Pattern.compile("refresh_token=([^&]*)");
    private static final Pattern expiresInPattern = Pattern.compile("expires_in=(\\d+)");

    private LoginPresenter loginPresenter;
    private AccountManager accountManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountManager = AccountManager.getInstance(this);
        loginPresenter = new LoginPresenter(new LoginModel(accountManager), this);

        loginWebView = findViewById(R.id.login_webview);

        loginPresenter.userLogin();

        Log.d("check", accountManager.getAccessToken());


    }

    @Override
    public void openLoginPage() {
        loginWebView.loadUrl("https://api.imgur.com/oauth2/authorize?client_id=559663b09041d88&response_type=token");
        loginWebView.getSettings().setJavaScriptEnabled(true);
        loginWebView.clearCache(true);
        loginWebView.clearHistory();
        loginWebView.clearFormData();
        CookieManager.getInstance().removeAllCookie();
        loginWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // intercept the tokens
                // http://example.com#access_token=ACCESS_TOKEN&token_type=Bearer&expires_in=3600

                if (url.startsWith(REDIRECT_URL)) {
                    Matcher m;

                    m = refreshTokenPattern.matcher(url);
                    m.find();
                    final String refreshToken = m.group(1);

                    m = accessTokenPattern.matcher(url);
                    m.find();
                    String accessToken = m.group(1);

                    m = expiresInPattern.matcher(url);
                    m.find();
                    long expiresIn = Long.valueOf(m.group(1));

                    Log.d("refreshToken", refreshToken);
                    Log.d("accessToken", accessToken);
                    Log.d("expiresIn", String.valueOf(expiresIn));

                    loginPresenter.saveToken(refreshToken, accessToken, expiresIn);
                }
                return true;
            }
        });

    }

    @Override
    public void loginFail() {

    }

    @Override
    public void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

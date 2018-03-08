package com.example.imgurupload.main;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imgurupload.ImgurAPI;
import com.example.imgurupload.album.AlbumFragment;
import com.example.imgurupload.api.ImgurApiService;
import com.example.imgurupload.api.RetrofitService;
import com.example.imgurupload.image.Image;
import com.example.imgurupload.image.ImageFragment;
import com.example.imgurupload.image.ImageManager;
import com.example.imgurupload.login.AccountManager;
import com.example.imgurupload.login.LoginActivity;
import com.example.imgurupload.R;
import com.google.gson.Gson;

import java.util.List;

import cz.msebera.android.httpclient.auth.AUTH;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

public class MainActivity extends AppCompatActivity implements MainContract.View,
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    MainPresenter mainPresenter;

    DrawerLayout drawer;
    NavigationView navigationView;
    LinearLayout header;
    TextView profileName;
    Toolbar toolbar;
    FloatingActionButton fab;
    ProgressBar progressBar;

    private static final int AUTHORIZATION = 100;

    private static final String IMAGE = "image";
    private static final String ALBUM = "album";

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        progressBar = findViewById(R.id.progressBar);

        getIntent().getStringExtra(IMAGE);

        navigationView = findViewById(R.id.nav_view);
        if (navigationView.getHeaderCount() > 0) {
            header = navigationView.getHeaderView(0).findViewById(R.id.header);
            profileName = navigationView.getHeaderView(0).findViewById(R.id.profile_name);

        }
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.drawer);

        mainPresenter = new MainPresenter(this, new MainModel());

        if(AccountManager.getInstance(this).isLogin()) {
            profileName.setText(AccountManager.getInstance(this).getAccountName());
        }

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.clickToLogin();
            }
        });

        mainPresenter.showFragment(this);

        fab.setOnClickListener(this);
    }

    @Override
    public void showNotLogin() {
    }

    @Override
    public void loadPhotos() {
        setTitle(R.string.photo);
        if(getFragmentManager().findFragmentByTag("imageFragment") != null) {
            getFragmentManager().popBackStackImmediate("imageFragment", POP_BACK_STACK_INCLUSIVE);
        }
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, ImageFragment.newInstance(""));
            ft.commit();
    }

    @Override
    public void loadAlbums() {
        setTitle(R.string.album);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, new AlbumFragment());
        ft.commit();
    }

    @Override
    public void loadAlbumImage(String id) {
        setTitle(R.string.photo);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, ImageFragment.newInstance(id));
        ft.addToBackStack("imageFragment");
        ft.commit();
    }

    @Override
    public void popUpLoginPage() {
        drawer.closeDrawers();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(intent, AUTHORIZATION);
    }

    @Override
    public void navigateToMainActivity() {

    }

    @Override
    public void loginFailed() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case AUTHORIZATION:
                    profileName.setText(AccountManager.getInstance(this).getAccountName());
                    mainPresenter.showFragment(this);
                    break;
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        //FragmentTransaction ft = getFragmentManager().beginTransaction();
        drawer.closeDrawers();

        switch (item.getItemId()) {
            case R.id.image:
                loadPhotos();
                break;
            case R.id.album:
                loadAlbums();
                break;
            case R.id.logout:
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
                AccountManager.getInstance(this).clearCache();
                mainPresenter.showFragment(this);
                break;
            default:
                Toast.makeText(this, "other", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        showBottomSheetDialog();
    }

    public void showBottomSheetDialog() {
        MainBottomSheetDialogFragment mainBottomSheetDialogFragment = new MainBottomSheetDialogFragment();
        mainBottomSheetDialogFragment.show(getSupportFragmentManager(), mainBottomSheetDialogFragment.getTag());
    }

    public void showProgress(int percent) {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(percent);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

}

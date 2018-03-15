package com.example.imgurupload.home;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.imgurupload.MainActivity;
import com.example.imgurupload.UploadService;
import com.example.imgurupload.album.AlbumFragment;
import com.example.imgurupload.api.ImgurApiService;
import com.example.imgurupload.api.RetrofitService;
import com.example.imgurupload.image.ImageFragment;
import com.example.imgurupload.login.AccountManager;
import com.example.imgurupload.login.LoginActivity;
import com.example.imgurupload.R;
import com.example.imgurupload.response.Avatar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

public class HomeActivity extends AppCompatActivity implements HomeContract.View,
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, HomeBottomSheetDialogFragment.UploadListener {

    HomePresenter homePresenter;

    DrawerLayout drawer;
    NavigationView navigationView;
    LinearLayout header;
    TextView accountName;
    ImageView avatarImage;
    Toolbar toolbar;
    FloatingActionButton fab;
    LinearLayout progressView;
    ProgressBar progressBar;
    TextView progressText;

    private static final int AUTHORIZATION = 100;

    private static final String IMAGE = "image";
    private static final String ALBUM = "album";

    public static final int UPLOADING = 100;
    public static final int UPLOAD_SUCCESS = 200;
    public static final int UPLOAD_FAIL = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        progressView = findViewById(R.id.progress_view);
        progressBar = findViewById(R.id.progress_bar);
        progressText = findViewById(R.id.progress_text);

        getIntent().getStringExtra(IMAGE);


        navigationView = findViewById(R.id.nav_view);
        if (navigationView.getHeaderCount() > 0) {
            header = navigationView.getHeaderView(0).findViewById(R.id.header);
            avatarImage = header.findViewById(R.id.avatar_image);
            accountName = header.findViewById(R.id.account);

        }
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.drawer);

        homePresenter = new HomePresenter(this, new HomeModel());

        if (AccountManager.getInstance(this).isLogin()) {
            accountName.setText(AccountManager.getInstance(this).getAccountName());
        }

        getAvatar();

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homePresenter.clickToLogin();
            }
        });
        if ("album".equals(getIntent().getStringExtra("album"))) {
            loadAlbums();
        } else {
            homePresenter.showFragment(this);
        }

        fab.setOnClickListener(this);
    }

    @Override
    public void showNotLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void loadPhotos() {
        setTitle(R.string.photo);
        if (getFragmentManager().findFragmentByTag("imageFragment") != null) {
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
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
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

        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case AUTHORIZATION:
                    accountName.setText(AccountManager.getInstance(this).getAccountName());
                    homePresenter.showFragment(this);
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
                AccountManager.getInstance(this).clearCache();
                homePresenter.showFragment(this);
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
        HomeBottomSheetDialogFragment mainBottomSheetDialogFragment = new HomeBottomSheetDialogFragment();
        mainBottomSheetDialogFragment.show(getSupportFragmentManager(), mainBottomSheetDialogFragment.getTag());
    }

    private void getAvatar() {
        String username = AccountManager.getInstance(this).getAccountName();
        RetrofitService.getInstance(this).createApi(ImgurApiService.class).getAvatar(username)
                .enqueue(new Callback<Avatar>() {
                    @Override
                    public void onResponse(Call<Avatar> call, Response<Avatar> response) {
                        String url = response.body().getData().getAvatar();
                        Glide.with(HomeActivity.this).load(url).into(avatarImage);
                    }

                    @Override
                    public void onFailure(Call<Avatar> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onUpload(ArrayList<Uri> uris) {

        final int total = uris.size();

        ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                switch (resultCode) {
                    case UPLOADING:
                        progressView.setVisibility(View.VISIBLE);
                        progressBar.setProgress(resultData.getInt("progress"));
                        progressText.setText(String.format("%d/%d", resultData.getInt("num"), total));
                        drawer.setVisibility(View.GONE);
                        break;
                    case UPLOAD_SUCCESS:
                        progressView.setVisibility(View.GONE);
                        drawer.setVisibility(View.VISIBLE);
                        loadPhotos();
                        break;
                    case UPLOAD_FAIL:
                        Toast.makeText(HomeActivity.this, R.string.upload_failed, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        Intent intent = new Intent(HomeActivity.this, UploadService.class);
        intent.putParcelableArrayListExtra("images", uris);
        intent.putExtra("receiver", resultReceiver);
        startService(intent);
    }
}

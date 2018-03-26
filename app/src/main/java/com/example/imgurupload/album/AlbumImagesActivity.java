package com.example.imgurupload.album;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.imgurupload.BaseResponse;
import com.example.imgurupload.ImageUtils;
import com.example.imgurupload.R;
import com.example.imgurupload.UploadService;
import com.example.imgurupload.api.ImgurApiService;
import com.example.imgurupload.api.RetrofitService;
import com.example.imgurupload.home.HomeActivity;
import com.example.imgurupload.image.Image;
import com.example.imgurupload.image.ImageAdapter;
import com.example.imgurupload.image.ImgurListener;
import com.example.imgurupload.login.AccountManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumImagesActivity extends AppCompatActivity implements ImgurListener {

    private static final String ALBUM_ID = "album_id";

    Toolbar toolbar;
    RecyclerView albumImagesRecyclerview;
    ImageAdapter imageAdapter;
    ArrayList<Image.DataBean> imageList = new ArrayList<>();
    String id;
    ArrayList<Uri> uploadedPhoto = new ArrayList<>();

    public static Intent createIntent(Context context, String albumId) {
        return new Intent(context, AlbumImagesActivity.class)
                .putExtra(ALBUM_ID, albumId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_images);

        toolbar = findViewById(R.id.toolbar);
        albumImagesRecyclerview = findViewById(R.id.album_images_recyclerview);

        setSupportActionBar(toolbar);
        setTitle("");
        id = getIntent().getStringExtra(ALBUM_ID);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imageAdapter = new ImageAdapter(imageList, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        albumImagesRecyclerview.setLayoutManager(layoutManager);
        albumImagesRecyclerview.setAdapter(imageAdapter);

        loadAlbumPhotos(id);
    }

    private void setTitle(String title) {
        toolbar.setTitle(title);
    }

    public void loadAlbumPhotos(String id) {
        RetrofitService.getInstance(this).createApi(ImgurApiService.class)
                .getAlbum(AccountManager.getInstance(this).getAccountName(), id)
                .enqueue(new Callback<Albums>() {
                    @Override
                    public void onResponse(Call<Albums> call, Response<Albums> response) {
                        Albums album = response.body();
                        setTitle(album.getData().getTitle());
                        ArrayList<Image.DataBean> images = new ArrayList<>(album.getData().getImages());
                        imageAdapter.update(images);
                    }

                    @Override
                    public void onFailure(Call<Albums> call, Throwable t) {
                        Log.d("error", t.getMessage());
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_album_images, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_photo:
                addPhotoToAlbum();
                return true;
            case R.id.modify_album_title:
                modifyAlbumTitle();
                return true;
            case R.id.delete_album:
                deleteAlbum();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onPhotoClick(int position) {

    }

    @Override
    public void onPhotoLongClick(int position) {

    }

    private void addPhotoToAlbum() {

        ImageUtils.createGalleryIntent(this, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_OK) {
            Uri uri = intent.getData();

            if (uri != null) {
                uploadedPhoto.add(uri);
            } else if (Build.VERSION.SDK_INT >= 16) {
                ClipData clipData = intent.getClipData();
                int count = clipData.getItemCount();
                if (count > 0) {
                    for (int i = 0; i < count; i++) {
                        uploadedPhoto.add(clipData.getItemAt(i).getUri());
                    }
                }
            }
            uploadPhoto(uploadedPhoto);
        }
    }

    private void modifyAlbumTitle() {

        final View view = LayoutInflater.from(this).inflate(R.layout.modify_album_title, null);

        new AlertDialog.Builder(this)
                .setTitle(R.string.modify_album_title)
                .setView(view)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText newTitle = view.findViewById(R.id.new_title);
                        updateAlbumTitle(newTitle.getText().toString());
                    }
                })
                .show();
    }

    private void updateAlbumTitle(String title) {

        RetrofitService.getInstance(this).createApi(ImgurApiService.class).updateAlbum(id, title).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    loadAlbumPhotos(id);
                } else {
                    Toast.makeText(AlbumImagesActivity.this, R.string.update_failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
            }
        });


    }

    private void deleteAlbum() {
        RetrofitService.getInstance(this).createApi(ImgurApiService.class).deleteAlbum(id).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, retrofit2.Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    finish();
                } else {
                    Toast.makeText(AlbumImagesActivity.this, R.string.delete_failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
            }
        });
    }

    private void uploadPhoto(ArrayList<Uri> uris) {

        ResultReceiver receiver = new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                super.onReceiveResult(resultCode, resultData);
                if(resultCode == HomeActivity.UPLOAD_SUCCESS) {
                    loadAlbumPhotos(id);
                }
            }
        };

        Intent intent = UploadService.createAlbumImagesIntent(this, uris, id);
        intent.putExtra("receiver", receiver);
        startService(intent);
    }
}

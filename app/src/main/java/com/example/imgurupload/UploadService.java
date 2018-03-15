package com.example.imgurupload;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.example.imgurupload.api.ImgurApiService;
import com.example.imgurupload.api.ProgressRequestBody;
import com.example.imgurupload.api.RetrofitService;
import com.example.imgurupload.image.Image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

import static com.example.imgurupload.home.HomeActivity.UPLOADING;
import static com.example.imgurupload.home.HomeActivity.UPLOAD_FAIL;
import static com.example.imgurupload.home.HomeActivity.UPLOAD_SUCCESS;

public class UploadService extends IntentService implements ProgressRequestBody.UploadCallbacks {

    private static final String TAG = UploadService.class.getSimpleName();
    private static final String IMAGES = "images";
    private static final String TITLE = "title";

    ResultReceiver resultReceiver;
    int total = 0;
    int num = 0;

    public UploadService() {
        super(TAG);
    }

    public static Intent createIntent(Context context, ArrayList<Uri> images, String title) {
        Intent intent = new Intent(context, UploadService.class)
                .putExtra(IMAGES, images);
        if (!TextUtils.isEmpty(title)) intent.putExtra(TITLE, title);
        return intent;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        resultReceiver = intent.getParcelableExtra("receiver");
        ArrayList<Uri> uris = intent.getParcelableArrayListExtra(IMAGES);
        String title = intent.getStringExtra(TITLE);
        String albumId = null;

        if (title != null) {
            albumId = createAlbum(title);
            uploadPhotos(uris, albumId);
        } else {
            uploadPhotos(uris, null);
        }
    }

    private void uploadPhotos(ArrayList<Uri> uris, String albumId) {
        total = uris.size();

        for (Uri uri : uris) {
            Response<Image.DataBean> response = null;
            try {
                File file = new File(FileUitls.getPath(this, uri));
                MultipartBody.Builder builder = new MultipartBody.Builder();
                ProgressRequestBody fileBody = new ProgressRequestBody(file, this);
                builder.addFormDataPart("image", file.getName(), fileBody);
                builder.addFormDataPart("type", "file");
                if (albumId != null)
                    builder.addFormDataPart("album", albumId);
                builder.addFormDataPart("name", file.getName());
                builder.setType(MultipartBody.FORM);
                MultipartBody multipartBody = builder.build();

                response = RetrofitService.getInstance(this).createApi(ImgurApiService.class)
                        .uploadImage(multipartBody).execute();

                num++;

                if (!response.isSuccessful()) {
                    resultReceiver.send(UPLOAD_FAIL, null);
                } else {
                    if (num >= total) {
                        resultReceiver.send(UPLOAD_SUCCESS, null);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String createAlbum(String title) {

        Response<com.example.imgurupload.api.Response> response = null;

        RequestBody requestBody = new FormBody.Builder()
                .add("title", title)
                .build();
        try {
            response = RetrofitService.getInstance(this)
                    .createApi(ImgurApiService.class).createAlbum(requestBody).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("albumId", response.body().getData().getId());
        return response.body().getData().getId();
    }

    @Override
    public void onProgressUpdate(final int percentage) {
        Log.d("percent", String.valueOf(percentage));
        Bundle bundle = new Bundle();
        bundle.putInt("num", num);
        bundle.putInt("progress", percentage);
        resultReceiver.send(UPLOADING, bundle);
    }

    @Override
    public void onError() {
    }

    @Override
    public void onFinish() {
    }
}

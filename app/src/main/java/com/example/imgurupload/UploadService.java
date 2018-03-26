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
    private static final String ALBUM_ID = "album_id";

    ResultReceiver resultReceiver;
    UploadNotification notification;

    int total = 0;   // 全部數量
    int num = 0;     // 正在傳第幾個
    int success = 0; // 成功上傳數

    public UploadService() {
        super(TAG);
    }

    public static Intent createImageIntent(Context context, ArrayList<Uri> images) {
        Intent intent = new Intent(context, UploadService.class)
                .putExtra(IMAGES, images);
        return intent;
    }

    public static Intent createAlbumIntent(Context context, ArrayList<Uri> images, String title) {
        Intent intent = createImageIntent(context, images);
        if (!TextUtils.isEmpty(title))
            intent.putExtra(TITLE, title);
        return intent;
    }

    public static Intent createAlbumImagesIntent(Context context, ArrayList<Uri> images, String albumId) {
        Intent intent = createImageIntent(context, images);
        if (!TextUtils.isEmpty(albumId))
            intent.putExtra(ALBUM_ID, albumId);
        return intent;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        notification = new UploadNotification(getApplicationContext());
        resultReceiver = intent.getParcelableExtra("receiver");
        ArrayList<Uri> uris = intent.getParcelableArrayListExtra(IMAGES);
        String title = intent.getStringExtra(TITLE);
        String albumId = intent.getStringExtra(ALBUM_ID);

        if (albumId == null && title != null) {
            albumId = createAlbum(title);
        }
        uploadPhotos(uris, albumId);
    }

    private void uploadPhotos(ArrayList<Uri> uris, String albumId) {
        total = uris.size();

        for (Uri uri : uris) {
            Response<Image.DataBean> response = null;
            notification.onUploading(total, num + 1);
            try {
                MultipartBody multipartBody = createImageBody(uri, albumId);
                response = RetrofitService.getInstance(this).createApi(ImgurApiService.class)
                        .uploadImage(multipartBody).execute();

                if (response.isSuccessful()) {
                    success++;
                }
                num++;

                if (num >= total) {
                    if (success < total) {
                        resultReceiver.send(UPLOAD_FAIL, null);
                        notification.onFailUpload();
                    } else {
                        resultReceiver.send(UPLOAD_SUCCESS, null);
                        notification.onSuccessUpload();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                notification.onFailUpload();
            }
        }
    }

    private MultipartBody createImageBody(Uri uri, String albumId) {
        File file = new File(FileUitls.getPath(this, uri));
        MultipartBody.Builder builder = new MultipartBody.Builder();
        ProgressRequestBody fileBody = new ProgressRequestBody(file, this);
        builder.addFormDataPart("image", file.getName(), fileBody);
        builder.addFormDataPart("type", "file");
        if (albumId != null)
            builder.addFormDataPart("album", albumId);
        builder.addFormDataPart("name", file.getName());
        builder.setType(MultipartBody.FORM);
        return builder.build();
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

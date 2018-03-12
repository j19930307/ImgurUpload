package com.example.imgurupload;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imgurupload.api.ImgurApiService;
import com.example.imgurupload.api.ProgressRequestBody;
import com.example.imgurupload.api.RetrofitService;
import com.example.imgurupload.image.Image;
import com.example.imgurupload.home.HomeActivity;
import com.loopj.android.http.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAlbumActivity extends AppCompatActivity implements ProgressRequestBody.UploadCallbacks {
    private RecyclerView recyclerView;
    private EditText editText;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private String title;
    private ArrayList<Uri> imagesPath;

    private RelativeLayout contentView;
    private LinearLayout progressView;
    private ProgressBar progressBar;
    private TextView progressText;
    Toolbar toolbar;

    FloatingActionButton fab;

    private int max = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_album);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.create_album);

        imagesPath = new ArrayList<>();

        recyclerView = findViewById(R.id.album_preview);
        editText = findViewById(R.id.album_title_text);
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CreateAlbumAdapter(imagesPath);
        recyclerView.setAdapter(adapter);

        contentView = findViewById(R.id.content_view);
        progressView = findViewById(R.id.progress_view);
        progressBar = findViewById(R.id.progressbar);
        progressText = findViewById(R.id.progress_text);

        fab = findViewById(R.id.pick_image);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();

            }
        });

        /*album_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String[] convertedImages = imageToBase64(imagesPath);
                progressView.setVisibility(View.VISIBLE);
                fab.setVisibility(View.GONE);
                title = editText.getText().toString();
                Log.v("title", title);
                //new UploadService(context).albumPost(title, convertedImages);
                createAlbum(title);
            }
        });*/
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_album, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_album:
                title = editText.getText().toString();
                createAlbum(title);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void createAlbum(String title) {
        max = imagesPath.size();
        progressBar.setMax(max);
        progressText.setText("0/" + max);

        RequestBody requestBody = new FormBody.Builder()
                .add("title", title)
                .build();

        RetrofitService.getInstance(this).createApi(ImgurApiService.class).createAlbum(requestBody)
                .enqueue(new Callback<com.example.imgurupload.api.Response>() {
            @Override
            public void onResponse(Call<com.example.imgurupload.api.Response> call, Response<com.example.imgurupload.api.Response> response) {
                String albumId = response.body().getData().getId();
                Log.d("album_id", String.valueOf(albumId));

                uploadImage(albumId);

            }

            @Override
            public void onFailure(Call<com.example.imgurupload.api.Response> call, Throwable t) {
                Log.d("fail", t.getMessage());
            }
        });
    }

    //single/multiple select images from android gallery
    protected void chooseImage(Context context) {
        String mimeType = "image/*";
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        final PackageManager packageManager = context.getPackageManager();
        intent.setType(mimeType);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent picker = new Intent(Intent.ACTION_GET_CONTENT);
            picker.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            picker.setType(mimeType);
            Intent selectImagePath = Intent.createChooser(picker, "Choose File");
            startActivityForResult(selectImagePath, 0);
        } else {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_OK) {
            Uri uri = intent.getData();

            if (uri != null) {
                imagesPath.add(uri);
            } else if (Build.VERSION.SDK_INT >= 16) {
                ClipData clipData = intent.getClipData();
                int count = clipData.getItemCount();
                if (count > 0) {
                    for (int i = 0; i < count; i++) {
                        imagesPath.add(clipData.getItemAt(i).getUri());
                    }
                }
            }
            if(imagesPath.size() > 0)
            adapter = new CreateAlbumAdapter(imagesPath);
            recyclerView.setAdapter(adapter);
        }
    }

    //selected images convert to base64 format
    private String[] imageToBase64(ArrayList<Uri> uris) {
        String[] base64images = new String[uris.size()];
        for (int i = 0; i < uris.size(); i++) {
            try {
                InputStream imageStream = getContentResolver().openInputStream(uris.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                byte[] bytes = baos.toByteArray();
                byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
                String encodeString = new String(encode);
                base64images[i] = encodeString;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return base64images;
    }

    public void showFileChooser() {
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        List activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;
        if (isIntentSafe) {
            Intent destIntent = Intent.createChooser(intent, getString(R.string.select_photos));
            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(destIntent, 500);
            }
        } else {
            Toast.makeText(this, R.string.not_install_picture_viewer, Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage(final String albumId) {

            Log.d("uri", imagesPath.get(0).toString());
            File file = new File(FileUitls.getPath(this, imagesPath.get(0)));

            MultipartBody.Builder builder = new MultipartBody.Builder();
            //RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);
            ProgressRequestBody fileBody = new ProgressRequestBody(file, this);
            builder.addFormDataPart("image", file.getName(), fileBody);
            builder.addFormDataPart("album", albumId);
            builder.addFormDataPart("type", "file");
            builder.addFormDataPart("name", file.getName());
            builder.setType(MultipartBody.FORM);
            MultipartBody multipartBody = builder.build();

            RetrofitService.getInstance(this).createApi(ImgurApiService.class)
                    .uploadImage(multipartBody).enqueue(
                    new Callback<Image.DataBean>() {
                        @Override
                        public void onResponse(Call<Image.DataBean> call, Response<Image.DataBean> response) {
                            Log.d("success", response.body().toString());
                            imagesPath.remove(0);
                            progressText.setText(String.valueOf(max-imagesPath.size()) + "/" + max);
                            progressBar.setProgress(max-imagesPath.size());
                            if(imagesPath.size() > 0) {
                                uploadImage(albumId);
                            } else {
                                //progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(CreateAlbumActivity.this, HomeActivity.class));
                            }
                        }

                        @Override
                        public void onFailure(Call<Image.DataBean> call, Throwable t) {
                            Log.d("fail", t.getMessage());
                        }
                    }
            );

    }

    @Override
    public void onProgressUpdate(int percentage) {
        Log.d("percentage", String.valueOf(percentage));
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {

    }
}

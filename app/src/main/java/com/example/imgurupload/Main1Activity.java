package com.example.imgurupload;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.Base64;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

//import com.facebook.stetho.Stetho;

public class Main1Activity extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        context = this;

        /*Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());*/

        Button select_button = (Button) findViewById(R.id.image_upload);
        select_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if (isConnected)
                    chooseImage(context);
                else
                    Toast.makeText(context, "網路未連接", Toast.LENGTH_SHORT).show();
            }
        });

        Button history_button = (Button) findViewById(R.id.history);
        history_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(Main1Activity.this, UploadHistoryActivity.class);
                startActivity(i);
            }
        });

        Button album_button = (Button) findViewById(R.id.album);
        album_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(Main1Activity.this, CreateAlbumActivity.class);
                startActivity(i);
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

    //upload base64 images to imgur
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_OK) {
            String[] base64images;
            Uri uri = intent.getData();

            if (uri != null) {
                base64images = imageToBase64(new Uri[]{uri});
                new UploadService(this).post(base64images);
            } else if (Build.VERSION.SDK_INT >= 16) {
                ClipData clipData = intent.getClipData();
                int count = clipData.getItemCount();
                base64images = new String[count];
                if (count > 0) {
                    Uri[] uris = new Uri[count];
                    for (int i = 0; i < count; i++) {
                        uris[i] = clipData.getItemAt(i).getUri();
                    }
                    base64images = imageToBase64(uris);
                }
                new UploadService(this).post(base64images);
            }
        } else {
        }
    }

    //selected images convert to base64 format
    private String[] imageToBase64(Uri[] uris) {
        String[] base64images = new String[uris.length];
        for (int i = 0; i < uris.length; i++) {
            try {
                InputStream imageStream = getContentResolver().openInputStream(uris[i]);
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


}
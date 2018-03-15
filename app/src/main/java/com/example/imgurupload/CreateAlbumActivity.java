package com.example.imgurupload;

import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imgurupload.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;


public class CreateAlbumActivity extends AppCompatActivity {
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
                contentView.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
                progressView.setVisibility(View.VISIBLE);

                ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
                    @Override
                    protected void onReceiveResult(int resultCode, Bundle resultData) {
                        if (resultCode != 200) {
                            progressView.setVisibility(View.VISIBLE);
                            progressBar.setProgress(resultData.getInt("progress"));
                            progressText.setText(String.format("%d/%d", resultData.getInt("num"), imagesPath.size()));
                        } else {
                            progressView.setVisibility(View.GONE);
                            Intent intent = new Intent(CreateAlbumActivity.this, HomeActivity.class);
                            intent.putExtra("album", "album");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }

                    }
                };

                Intent intent = UploadService.createIntent(this, imagesPath, title);
                intent.putExtra("receiver", resultReceiver);
                startService(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
            if (imagesPath.size() > 0)
                adapter = new CreateAlbumAdapter(imagesPath);
            recyclerView.setAdapter(adapter);
        }
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
}

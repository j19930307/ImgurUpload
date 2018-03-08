package com.example.imgurupload.image;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.imgurupload.ImageUtils;
import com.example.imgurupload.R;

public class ImageDetailsActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);

        Image.DataBean data = getIntent().getParcelableExtra("image");

        image = findViewById(R.id.image);
        Glide.with(this)
                .load(data.getLink())
                .fitCenter()
                .into(image);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image_details, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
                break;
            case R.id.copy:
                Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}

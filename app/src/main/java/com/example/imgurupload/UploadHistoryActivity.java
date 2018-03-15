package com.example.imgurupload;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UploadHistoryActivity extends AppCompatActivity {

    CustomViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_history);

        Intent intent = getIntent();
        String position = intent.getStringExtra("position");

        // Find the view pager that will allow the user to swipe between fragments
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        if("album".equals(position))
            viewPager.setCurrentItem(1);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        //Intent intent = new Intent(this, Main1Activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(intent);
        finish();
    }
}

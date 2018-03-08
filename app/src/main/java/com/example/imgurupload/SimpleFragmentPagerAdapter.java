package com.example.imgurupload;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.imgurupload.image.ImageFragment;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] tabTitle = {"圖片", "相簿"};

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        /*if (position == 0) {
            return new ImageFragment();
        } else if (position == 1) {
            return new AlbumFragment();
        } else {
            return new ImageFragment();
        }*/
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }

}

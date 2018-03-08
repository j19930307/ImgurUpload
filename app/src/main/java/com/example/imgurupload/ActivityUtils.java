package com.example.imgurupload;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

public class ActivityUtils {

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment, tag);
        transaction.commit();
    }

    public static void replaceFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment, tag);
        transaction.commit();
    }
}

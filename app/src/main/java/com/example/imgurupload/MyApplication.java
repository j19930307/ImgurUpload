package com.example.imgurupload;

import android.app.Application;

public class MyApplication extends Application {

    private static MyApplication instance;

    public static MyApplication getInstance() {
        if (instance == null) {
            return new MyApplication();
        } else {
            return instance;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


}

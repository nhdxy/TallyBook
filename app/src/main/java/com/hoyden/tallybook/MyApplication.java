package com.hoyden.tallybook;

import android.app.Application;

/**
 * Created by nhd on 2017/4/7.
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }
}

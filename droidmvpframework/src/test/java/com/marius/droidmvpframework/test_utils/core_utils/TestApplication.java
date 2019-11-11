package com.marius.droidmvpframework.test_utils.core_utils;

import android.app.Application;

import com.marius.droidmvpframework.R;

public class TestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setTheme(R.style.Theme_AppCompat); //or just R.style.Theme_AppCompat
    }

}

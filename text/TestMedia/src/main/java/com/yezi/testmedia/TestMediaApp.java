package com.yezi.testmedia;

import android.app.Application;
import android.content.Context;

public class TestMediaApp extends Application {

    private static Context sAppContext;
    private static TestMediaApp mInstance;

    @Override public void onCreate() {
        super.onCreate();
        mInstance = this;
        sAppContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return sAppContext;
    }
}

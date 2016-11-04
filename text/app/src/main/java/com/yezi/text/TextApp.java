package com.yezi.text;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

public class TextApp extends Application {
    private static Context sAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = getApplicationContext();

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(sAppContext)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, config);
    }


    public static Context getAppContext() {
        return sAppContext;
    }

    public static Resources getAppResources() {
        return getAppContext().getResources();
    }

    public static void showToast(String toast) {
        Toast.makeText(getAppContext(), toast, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(@StringRes int toastRes) {
        Toast.makeText(getAppContext(), toastRes, Toast.LENGTH_SHORT).show();
    }

}

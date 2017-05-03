package com.yezi.testmedia.utils;

import android.os.Environment;

import java.io.File;

public class FileUtils {

    public static String getFilePath() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "TestMedia");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }
}

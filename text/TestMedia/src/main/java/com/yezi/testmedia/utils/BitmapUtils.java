package com.yezi.testmedia.utils;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.text.TextUtils;

import com.yezi.testmedia.TestMediaApp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtils {

    public static synchronized void saveBitmap(Bitmap bitmap, final String filePath) {
        if (bitmap == null) {
            return;
        }
        File file;
        if (TextUtils.isEmpty(filePath)) {
            file = new File(TestMediaApp.getAppContext().getExternalMediaDirs()[0].getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        } else {
            file = new File(filePath);
        }
        try {
            FileOutputStream fout = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            bitmap.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.yezi.text.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.yezi.text.R;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpActivity extends AppCompatActivity {

    private TextView mTextView;
    private OkHttpClient mClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_okhttp);

        mTextView = (TextView) findViewById(R.id.text_view);

        long cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(new File(getApplication().getCacheDir(), "http"), cacheSize);
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mClient = new OkHttpClient.
                Builder().
                cache(cache).
                addInterceptor(logInterceptor).
                build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                final String response = getString("https://raw.github.com/square/okhttp/master/README.md");
                OkHttpActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText(response);
                    }
                });
            }
        }).start();
    }

    private String getString(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = mClient.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "null";
    }
}

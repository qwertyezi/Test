package com.yezi.text.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yezi.text.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TransformActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transform);
        mTextView = (TextView) findViewById(R.id.text);
    }

    public void StartClick(View view) {

    }

    private void transform() throws IOException {
        int lastNumber=0;
        String response;
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("citylist:[\n");
        InputStream inputStream = getResources().getAssets().open("region_zh.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        while ((response = reader.readLine()) != null) {
            int numberLine = getNumberLine(response);
            if (numberLine == 0) {
                sb.append("{\n");
                sb.append("country:").append(getCityName(response)).append(",\n");
            }
            if(numberLine==1) {

            }
        }
    }

    private int getNumberLine(String response) {
        int number = 0;
        char[] array = response.toCharArray();
        for (char c : array) {
            if ("_".equals(c)) ++number;
        }
        return number;
    }

    private String getCityName(String response) {
        return response.substring(response.indexOf("|"));
    }
}

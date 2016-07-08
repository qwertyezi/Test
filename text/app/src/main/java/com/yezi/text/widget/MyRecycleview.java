package com.yezi.text.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;

import com.yezi.text.activity.AdapterSampleActivity;
import com.yezi.text.activity.AnimatorSampleActivity;
import com.yezi.text.R;

public class MyRecycleview extends AppCompatActivity {
    private boolean enabledGrid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycycleview);

        findViewById(R.id.btn_animator_sample).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyRecycleview.this, AnimatorSampleActivity.class);
                i.putExtra("GRID", enabledGrid);
                startActivity(i);
            }
        });

        findViewById(R.id.btn_adapter_sample).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyRecycleview.this, AdapterSampleActivity.class);
                i.putExtra("GRID", enabledGrid);
                startActivity(i);
            }
        });

        ((SwitchCompat) findViewById(R.id.grid)).setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        enabledGrid = isChecked;
                    }
                });
    }
}

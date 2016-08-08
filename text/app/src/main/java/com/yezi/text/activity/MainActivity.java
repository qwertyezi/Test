package com.yezi.text.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yezi.text.R;
import com.yezi.text.widget.MyRecycleview;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btRecycleview;
    private Button btSearch;
    private Button btRecycleviewItemTouchHelper;
    private Button btStartUpAnimation;
    private Button btImageLoader;
    private Button btLikeAnimation;
    private Button btAnnotationProcessing;
    private Button btCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btSearch = (Button) findViewById(R.id.searchview);
        btRecycleview = (Button) findViewById(R.id.recycleview_adapter_animation);
        btRecycleviewItemTouchHelper = (Button) findViewById(R.id.recycleview_itemtouchhelper);
        btStartUpAnimation = (Button) findViewById(R.id.canvas);
        btImageLoader = (Button) findViewById(R.id.imageloader);
        btLikeAnimation = (Button) findViewById(R.id.likeanimation);
        btAnnotationProcessing=(Button) findViewById(R.id.annotationProcessing);
        btCalendar=(Button)findViewById(R.id.btn_calendar);

        btSearch.setOnClickListener(this);
        btRecycleview.setOnClickListener(this);
        btRecycleviewItemTouchHelper.setOnClickListener(this);
        btStartUpAnimation.setOnClickListener(this);
        btImageLoader.setOnClickListener(this);
        btLikeAnimation.setOnClickListener(this);
        btAnnotationProcessing.setOnClickListener(this);
        btCalendar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchview:
                this.startActivity(new Intent(this, SearchViewActivity.class));
                break;
            case R.id.recycleview_adapter_animation:
                this.startActivity(new Intent(this, MyRecycleview.class));
                break;
            case R.id.recycleview_itemtouchhelper:
                this.startActivity(new Intent(this, RecycleviewItemtouchhelperActivity.class));
                break;
            case R.id.canvas:
                this.startActivity(new Intent(this, CanvasActivity.class));
                break;
            case R.id.imageloader:
                this.startActivity(new Intent(this, ImageLoaderActivity.class));
                break;
            case R.id.likeanimation:
                this.startActivity(new Intent(this, LikeAnimationActivity.class));
                break;
            case R.id.annotationProcessing:
                this.startActivity(new Intent(this, OkhttpTestActivity.class));
                break;
            case R.id.btn_calendar:
                this.startActivity(new Intent(this, CalendarActivity.class));
                break;
        }
    }

    public void BezierClick(View view) {
        this.startActivity(new Intent(this,BezierActivity.class));
    }

    public void DataBindingClick(View view) {
        this.startActivity(new Intent(this,DataBindingActivity.class));
    }

    public void DataBindingClick2(View view) {
        this.startActivity(new Intent(this,DataBinding2Activity.class));
    }

    public void TouchClick(View view) {
        this.startActivity(new Intent(this,TouchActivity.class));
    }

    public void OkHttpClick(View view) {
        this.startActivity(new Intent(this,OkHttpActivity.class));
    }
}

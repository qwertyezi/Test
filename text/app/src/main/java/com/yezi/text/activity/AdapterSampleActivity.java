package com.yezi.text.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jakewharton.scalpel.ScalpelFrameLayout;
import com.yezi.text.R;
import com.yezi.text.adapter.MainAdapter;
import com.yezi.text.adapter.SlideInBottomAnimationAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class AdapterSampleActivity extends AppCompatActivity {

    private static String[] data = new String[]{
            "Apple", "Ball", "Camera", "Day", "Egg", "Foo", "Google", "Hello", "Iron", "Japan", "Coke",
            "Dog", "Cat", "Yahoo", "Sony", "Canon", "Fujitsu", "USA", "Nexus", "LINE", "Haskell", "C++",
            "Java", "Go", "Swift", "Objective-c", "Ruby", "PHP", "Bash", "ksh", "C", "Groovy", "Kotlin",
            "Chip", "Japan", "U.S.A", "San Francisco", "Paris", "Tokyo", "Silicon Valley", "London",
            "Spain", "China", "Taiwan", "Asia", "New York", "France", "Kyoto", "Android", "Google",
            "iPhone", "iPad", "iPod", "Wasabeef"
    };
    private PtrFrameLayout mPtrFrameLayout;
    private StartUpPtrHeader mStartUpPtrHeader;
    private ScalpelFrameLayout mScalpelFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_sample);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.pull_refresh);

        mScalpelFrameLayout = (ScalpelFrameLayout) findViewById(R.id.scalpel);

        mScalpelFrameLayout.setLayerInteractionEnabled(true);
        mScalpelFrameLayout.setDrawIds(true);
        mScalpelFrameLayout.setDrawViews(true);

        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, recyclerView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrameLayout.refreshComplete();
                    }
                }, 3000);
            }
        });

        if (getIntent().getBooleanExtra("GRID", true)) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

//        recyclerView.setItemAnimator(new FadeInAnimator());
        MainAdapter adapter = new MainAdapter(this, new ArrayList<>(Arrays.asList(data)));
//        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
//        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        SlideInBottomAnimationAdapter slideInBottomAdapter = new SlideInBottomAnimationAdapter(adapter);
//        SlideInLeftAnimationAdapter slideInLeftAdapter = new SlideInLeftAnimationAdapter(adapter);
//        slideInBottomAdapter.setFirstOnly(false);
//        slideInBottomAdapter.setInterpolator(new DecelerateInterpolator(0.5f));
        recyclerView.setAdapter(slideInBottomAdapter);

        mStartUpPtrHeader = new StartUpPtrHeader(this);
        mPtrFrameLayout.setHeaderView(mStartUpPtrHeader);
        mPtrFrameLayout.addPtrUIHandler(mStartUpPtrHeader);
    }
}

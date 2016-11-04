package com.yezi.text.widget.RotatePager;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by yezi on 16-11-4.
 */

public class RotatePager extends HorizontalScrollView {

    private LinearLayout mLinearLayout;
    private RotatePagerAdapter mRotatePagerAdapter;


    public RotatePager(Context context) {
        this(context, null);
    }

    public RotatePager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotatePager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public void setAdapter(RotatePagerAdapter adapter) {
        if (adapter == null) {
            return;
        }
        mRotatePagerAdapter = adapter;
        mRotatePagerAdapter.registerDataSetObserver(mRotatePagerDataSetObserver);
    }

    private void init() {
        if (mLinearLayout == null) {
            initLinearLayout();
        }
        addView(mLinearLayout);
    }

    private void initLinearLayout() {
        mLinearLayout = new LinearLayout(getContext());
        mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLinearLayout.setLayoutParams(lp);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mRotatePagerAdapter != null) {
            mRotatePagerAdapter.unregisterDataSetObserver(mRotatePagerDataSetObserver);
        }
    }

    private DataSetObserver mRotatePagerDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            for (int i = 0; i < mRotatePagerAdapter.getCount(); i++) {
                mLinearLayout.addView(mRotatePagerAdapter.getView(i, null, mLinearLayout));
            }
        }
    };
}

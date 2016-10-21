package com.yezi.text.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yezi.text.activity.MyFragment;

public class MyViewpagerAdapter extends FragmentPagerAdapter {

    private static final String[] titles = new String[]{"page1", "page2", "page3", "page4",
            "page5", "page6", "page7", "page8"};

    public MyViewpagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new MyFragment();
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}

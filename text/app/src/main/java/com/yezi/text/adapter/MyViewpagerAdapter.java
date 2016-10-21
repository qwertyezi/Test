package com.yezi.text.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yezi.text.activity.MyFragment;

public class MyViewpagerAdapter extends FragmentPagerAdapter {

    private static final String[] titles = new String[]{"对方水电费", "都是", "水电费水电费水电费是", "豆腐坊",
            "是的是的所", "都是", "水淀粉收多少", "是多少"};

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

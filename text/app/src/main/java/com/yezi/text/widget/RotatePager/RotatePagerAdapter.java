package com.yezi.text.widget.RotatePager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yezi on 16-11-4.
 */

public class RotatePagerAdapter extends BaseAdapter {

    private static final int IMAGE_WIDTH = 300;
    private static final int DELTA = 30;

    private List<String> mData;
    private Context mContext;

    public RotatePagerAdapter(Context context) {
        mData = new ArrayList<>();
        mContext = context;
    }

    public void updateData(List<String> list) {
        if (list != null && list.size() > 0) {
            mData.clear();
            mData.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SimpleDraweeView simpleDraweeView = new SimpleDraweeView(mContext);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                getImageWidthByPosition(position), getImageWidthByPosition(position));
        simpleDraweeView.setLayoutParams(lp);
        simpleDraweeView.setImageURI(UriUtil.parseUriOrNull(mData.get(position)));
        return simpleDraweeView;
    }

    private int getImageWidthByPosition(int position) {
        int distancePosition;
        if (position % 2 == 0) {
            distancePosition = Math.abs(mData.size() / 2 - position);
        } else {
            distancePosition = Math.abs((mData.size() + 1) / 2 - position - 1);
        }
        return IMAGE_WIDTH - DELTA * distancePosition;
    }
}

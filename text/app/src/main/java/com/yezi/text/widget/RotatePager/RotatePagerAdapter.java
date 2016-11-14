package com.yezi.text.widget.RotatePager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.common.util.UriUtil;

import java.util.ArrayList;
import java.util.List;

public class RotatePagerAdapter extends BaseAdapter {

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
        WrappingImage image = new WrappingImage(mContext);
        image.setPosition(position);
        image.setCount(getCount());
        image.setImageURI(UriUtil.parseUriOrNull(mData.get(position)));
        return image;
    }

}

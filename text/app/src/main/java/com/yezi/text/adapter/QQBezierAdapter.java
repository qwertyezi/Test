package com.yezi.text.adapter;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.yezi.text.R;
import com.yezi.text.utils.ScreenUtil;
import com.yezi.text.widget.QQBezierView;

public class QQBezierAdapter extends RecyclerView.Adapter<QQBezierAdapter.QQBezierVH> {

    @Override
    public QQBezierVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new QQBezierVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_qqbezier, parent, false));
    }

    @Override
    public void onBindViewHolder(QQBezierVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public static class QQBezierVH extends RecyclerView.ViewHolder {

        private FrameLayout mLayout;

        public QQBezierVH(View v) {
            super(v);
            mLayout = (FrameLayout) v.findViewById(R.id.layout_content);

            final WindowManager windowManager = (WindowManager) v.getContext().
                    getSystemService(Context.WINDOW_SERVICE);
            final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            final QQBezierView qqBezierView = new QQBezierView(v.getContext());

            mLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mLayout.requestDisallowInterceptTouchEvent(true);
                            params.width = ScreenUtil.getScreenWidth(v.getContext());
                            params.height = ScreenUtil.getScreenHeight(v.getContext());
                            params.format = PixelFormat.RGBA_8888;
                            params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                            int[] location = new int[2];
                            mLayout.getLocationOnScreen(location);
                            final float originX = location[0] + mLayout.getWidth() / 2;
                            final float originY = location[1] + mLayout.getHeight() / 2;
                            qqBezierView.setTranslate(originX, originY);
                            qqBezierView.setOnExplodeListener(new QQBezierView.onExplodeListener() {
                                @Override
                                public void onExplode() {
                                    windowManager.removeView(qqBezierView);
                                }
                            });
                            windowManager.addView(qqBezierView, params);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                    }
                    qqBezierView.onTouchEvent(event);
                    return true;
                }
            });
        }
    }
}

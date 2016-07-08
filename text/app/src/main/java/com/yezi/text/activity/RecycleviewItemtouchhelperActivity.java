package com.yezi.text.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.scalpel.ScalpelFrameLayout;
import com.yezi.text.R;
import com.yezi.text.adapter.ItemTouchHelperAdapter;
import com.yezi.text.adapter.ItemTouchHelperViewHolder;
import com.yezi.text.adapter.OnStartDragListener;
import com.yezi.text.adapter.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecycleviewItemtouchhelperActivity extends AppCompatActivity
        implements ItemTouchHelperAdapter, OnStartDragListener {
    private List<String> data;
    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;
    private SimpleItemTouchHelperCallback mCallback;
    private ItemTouchHelper mTouchHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_itemtouchhelper);

        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mMyAdapter = new MyAdapter(this);
        mRecyclerView.setAdapter(mMyAdapter);

        mCallback = new SimpleItemTouchHelperCallback(this);
        mTouchHelper = new ItemTouchHelper(mCallback);
        mTouchHelper.attachToRecyclerView(mRecyclerView);


    }

    private void initData() {
        if (data == null) {
            data = new ArrayList<>();
        }
        for (int i = 0; i < 100; i++) {
            data.add("Number:" + i);
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(data, fromPosition, toPosition);
        mMyAdapter.notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        data.remove(position);
        mMyAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mTouchHelper.startDrag(viewHolder);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private OnStartDragListener mDragListener;

        public MyAdapter(OnStartDragListener dragListener) {
            mDragListener = dragListener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemtouchhelper_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            holder.bind(data.get(position));
            holder.mImageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) ==
                            MotionEvent.ACTION_DOWN) {
                        mDragListener.onStartDrag(holder);
                    }
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        private final TextView mTextView;
        private final ImageView mImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_content);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_drag);
        }

        public void bind(String text) {
            mTextView.setText(text);
        }

        @Override
        public void onItemSelected() {
//            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
//            itemView.setBackgroundColor(0);
        }
    }
}



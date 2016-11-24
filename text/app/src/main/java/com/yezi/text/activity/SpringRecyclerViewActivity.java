package com.yezi.text.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yezi.text.R;
import com.yezi.text.widget.SpringRecyclerView;

public class SpringRecyclerViewActivity extends AppCompatActivity {

    private SpringRecyclerView mSpringRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_spring_recyclerview);

        mSpringRecyclerView = (SpringRecyclerView) findViewById(R.id.recycler_view);

        initView();
    }

    private void initView() {
        mSpringRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter adapter = new Adapter();
        mSpringRecyclerView.setAdapter(adapter);
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.VH> {

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(getLayoutInflater().inflate(R.layout.list_item_spring_recyclerview, parent, false));
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        class VH extends RecyclerView.ViewHolder {

            public VH(View itemView) {
                super(itemView);
            }

            public void bind(final int position) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "Click:" + position + "!!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}

package com.yezi.text.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yezi.text.BR;
import com.yezi.text.R;
import com.yezi.text.activity.BindingViewHolder;
import com.yezi.text.module.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserAdapter extends RecyclerView.Adapter<BindingViewHolder> {

    private List<User> mUserList;
    private final LayoutInflater mLayoutInflater;

    public UserAdapter(Context context) {
        mUserList = new ArrayList<>();
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position) {
        User user = mUserList.get(position);
        if (user.isFlag()) {
            return R.layout.item_user_one;
        } else {
            return R.layout.item_user_two;
        }
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding;
        if (viewType == R.layout.item_user_one) {
            binding = DataBindingUtil.inflate(mLayoutInflater, R.layout.item_user_one, parent, false);
        } else {
            binding = DataBindingUtil.inflate(mLayoutInflater, R.layout.item_user_two, parent, false);
        }
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        User user = mUserList.get(position);
        holder.getBinding().setVariable(BR.user, user);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public void addUsers(List<User> userList) {
        if (userList != null) {
            mUserList.addAll(userList);
            notifyDataSetChanged();
        }
    }

    Random mRandom = new Random(System.currentTimeMillis());

    public void addUser(User user) {
        int position = mRandom.nextInt(mUserList.size() + 1);
        mUserList.add(position, user);
        notifyItemInserted(position);
    }

    public void removeUser() {
        if (mUserList.size() == 0) {
            return;
        }
        int position = mRandom.nextInt(mUserList.size());
        mUserList.remove(position);
        notifyItemRemoved(position);
    }
}

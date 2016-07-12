package com.yezi.text.activity;

import android.databinding.DataBindingUtil;
import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

import com.yezi.text.R;
import com.yezi.text.adapter.UserAdapter;
import com.yezi.text.databinding.ActivityDataBinding2Binding;
import com.yezi.text.module.User;

import java.util.ArrayList;
import java.util.List;

public class DataBinding2Activity extends AppCompatActivity {

    private ActivityDataBinding2Binding mBinding;
    private UserAdapter mUserAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding2);
        mBinding.addOnRebindCallback(new OnRebindCallback() {
            @Override
            public boolean onPreBind(ViewDataBinding binding) {
                ViewGroup sceneRoot = (ViewGroup) binding.getRoot();
                TransitionManager.beginDelayedTransition(sceneRoot);
                return true;
            }
        });
        mBinding.setPresenter(new Presenter());

        mUserAdapter = new UserAdapter(this);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.setAdapter(mUserAdapter);

        List<User> data = new ArrayList<>();
        data.add(new User("001", "dfsffd", true));
        data.add(new User("002", "gdfsg", false));
        data.add(new User("003", "dfsdfgdsfgdsgffd", false));
        data.add(new User("004", "rtyryrtyrt", true));
        data.add(new User("005", "cvbcvb cbcb", false));
        mUserAdapter.addUsers(data);
    }

    public class Presenter {
        public void addItem(View view) {
            mUserAdapter.addUser(new User("666", "yiyiyyiyiyi", true));
        }

        public void removeItem(View view) {
            mUserAdapter.removeUser();
        }
    }

}

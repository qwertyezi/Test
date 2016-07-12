package com.yezi.text.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.yezi.text.R;
import com.yezi.text.databinding.ActivityDataBindingBinding;
import com.yezi.text.module.User;

public class DataBindingActivity extends AppCompatActivity {

    User user = new User("Test", "User");
    ObservableBoolean gradient = new ObservableBoolean();
    private ActivityDataBindingBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        gradient.set(true);
        mBinding.setUser(user);
        mBinding.setPresenter(new Presenter());
        mBinding.setGradient(gradient);
        mBinding.viewStub.getViewStub().inflate();
    }

    public class Presenter {
        public void onClick(View view) {
            Toast.makeText(DataBindingActivity.this, "点击", Toast.LENGTH_LONG).show();
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            user.setFirstName(s.toString());
        }

        public void onChangeGradientBoolean(View view) {
            gradient.set(!gradient.get());
        }
    }

    @BindingAdapter("fadeVisible")
    public static void setFadeVisible(final View view, boolean visible) {
        view.animate().cancel();

        if (visible) {
            view.setVisibility(View.VISIBLE);
            view.setAlpha(0);
            view.animate().alpha(1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setAlpha(1);
                }
            });
        } else {
            view.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setAlpha(1);
                    view.setVisibility(View.GONE);
                }
            });
        }
    }
}

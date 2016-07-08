package com.yezi.text.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yezi.text.R;
import com.yezi.text.utils.CalendarUtil;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
    }

    public void showCalendar(View view) {
        CalendarUtil.showCalendar();
    }

    public void addAccount(View view) {
        CalendarUtil.addAccount();
    }

    public void addEvent(View view) {
        CalendarUtil.addEvent("测试", "测试测试测试", "北京", 1460360519, 1460533319);
    }

    public void deleteEvent(View view) {
        CalendarUtil.deleteEvent();
    }
}

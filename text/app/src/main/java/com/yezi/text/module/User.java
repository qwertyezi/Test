package com.yezi.text.module;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.yezi.text.BR;


public class User extends BaseObservable {
    private String firstName;
    private String lastName;
    private boolean flag = true;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String firstName, String lastName, boolean flag) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.flag = flag;
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}

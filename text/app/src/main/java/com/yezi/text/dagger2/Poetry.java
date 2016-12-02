package com.yezi.text.dagger2;

import javax.inject.Inject;

public class Poetry {
    private String mPemo;

    // 用Inject标记构造函数,表示用它来注入到目标对象中去
//    @Inject
//    public Poetry() {
//        mPemo = "生活就像海洋";
//    }

    @Inject
    public Poetry(String pemos) {
        mPemo = pemos;
    }

    public String getPemo() {
        return mPemo;
    }
}
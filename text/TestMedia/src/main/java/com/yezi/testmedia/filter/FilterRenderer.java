package com.yezi.testmedia.filter;

public interface FilterRenderer {

    void onSurfaceCreated();

    void onSurfaceChanged(int width, int height);

    void onDrawFrame();
}

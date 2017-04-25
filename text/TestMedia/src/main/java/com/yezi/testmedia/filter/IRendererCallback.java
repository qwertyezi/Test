package com.yezi.testmedia.filter;

public interface IRendererCallback {

    void onSurfaceCreated();

    void onSurfaceChanged(int width, int height);

    void onDrawFrame();
}

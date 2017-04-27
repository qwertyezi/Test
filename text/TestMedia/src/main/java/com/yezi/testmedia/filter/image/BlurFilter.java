package com.yezi.testmedia.filter.image;

import com.yezi.testmedia.R;
import com.yezi.testmedia.filter.BaseFilter;

public class BlurFilter extends BaseFilter {


    public BlurFilter() {
        super(0, R.raw.blur_image_fragment);
    }

    @Override
    public void onCreated(int mProgram) {
    }

    @Override
    public void onDraw() {
    }

    @Override
    public void onChanged(int width, int height) {
    }
}

package com.yezi.text.dagger2;

import com.yezi.text.activity.Dagger2Activity;
import com.yezi.text.activity.Dagger2OtherActivity;

import dagger.Component;

@PoetryScope
@Component(modules = {MainModule.class, PoetryModule.class})
public abstract class MainComponent {

    abstract public void inject(Dagger2Activity activity);

    abstract public void inject(Dagger2OtherActivity activity);

    private static MainComponent sComponent;

    public static MainComponent getInstance() {
        if (sComponent == null) {
            sComponent = DaggerMainComponent.builder().build();
        }
        return sComponent;
    }
}

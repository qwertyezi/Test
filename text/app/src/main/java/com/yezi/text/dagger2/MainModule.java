package com.yezi.text.dagger2;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @Provides
    public Gson provideGson() {
        return new Gson();
    }
}

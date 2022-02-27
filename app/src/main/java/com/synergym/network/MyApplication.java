package com.synergym.network;

import android.app.Application;

public class MyApplication extends Application {

    private ApiComponent mApiComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApiComponent = DaggerApiComponent.builder()
                .appModule(new AppModule(this))
                .apiClient(new ApiClient("https://newsapi.org/v2/"))
                .build();


    }

    public ApiComponent getNetComponent() {
        return mApiComponent;
    }
}
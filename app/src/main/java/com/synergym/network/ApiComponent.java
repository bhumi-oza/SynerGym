package com.synergym.network;

import com.synergym.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiClient.class})
public interface ApiComponent {
    void inject(MainActivity activity);
}
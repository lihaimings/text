package com.haiming.myapplication.dagger;

import dagger.Module;
import dagger.Provides;

//Module的作用是用来提供生成依赖对象的

@Module
public class MainModule {
    private MainActivity mainActivity;

    public MainModule(MainActivity activity){
        mainActivity=activity;
    }

    @Provides
    public MainActivity providesActivity(){
        return mainActivity;
    }

    @Provides
    public User ProvidesUser(){
        return new User("daming");
    }

    @Provides
    public MainPresenter providerMainPresenter(MainActivity activity,User user){
        return new MainPresenter(activity,user);
    }
}

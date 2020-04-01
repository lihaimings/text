package com.haiming.myapplication.dagger;

import dagger.Component;

@Component (modules = MainModule.class)
public interface MainComponent {

    //参数要写成对应的activity
    void inject(MainActivity activity);
}

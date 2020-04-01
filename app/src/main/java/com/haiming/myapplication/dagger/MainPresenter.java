package com.haiming.myapplication.dagger;

public class MainPresenter {
    MainActivity activity;
    User user;

    public MainPresenter(MainActivity activity,User user){
        this.user=user;
        this.activity=activity;
    }

    public void showUserName(){
        activity.showUserName(user.name);
    }
}

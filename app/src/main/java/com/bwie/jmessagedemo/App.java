package com.bwie.jmessagedemo;

import android.app.Application;

import cn.jpush.im.android.api.JMessageClient;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JMessageClient.setDebugMode(true);//调试模式
        //初始化极光im
        JMessageClient.init(this,true);

    }
}

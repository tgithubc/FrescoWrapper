package com.tgithubc.FrescoWapper;

import android.app.Application;

import com.tgithubc.fresco_wapper.load.impl.FrescoImageLoader;


/**
 * Created by tiancheng :)
 */
public class App extends Application {

    private static App singleton;

    public static App getInstance() {
        return singleton;
    }

    @Override
    public final void onCreate() {
        super.onCreate();
        singleton = this;
        FrescoImageLoader.getInstance().initialize(this);
    }
}

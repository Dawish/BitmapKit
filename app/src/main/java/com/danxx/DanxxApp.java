package com.danxx;

import android.app.Application;

import danxx.bitmapkit.BitmapKitConfig;

/**
 * Created by Administrator on 2018/1/15.
 */

public class DanxxApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        BitmapKitConfig.initialize(this, 5*1024*1024);

    }
}

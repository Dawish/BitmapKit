package com.danxx;

import android.app.Application;

import com.bulong.rudeness.RudenessScreenHelper;
import com.facebook.drawee.backends.pipeline.Fresco;

import danxx.bitmapkit.BitmapKitConfig;

/**
 * Created by Administrator on 2018/1/15.
 */

public class DanxxApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);
        BitmapKitConfig.initialize(this, 5*1024*1024);

        //设计图标注的宽度
        int designWidth = 720;
        new RudenessScreenHelper(this, designWidth).activate();

    }
}

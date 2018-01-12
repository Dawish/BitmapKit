package com.danxx.utils;

import android.graphics.Bitmap;
import android.util.Log;

import hugo.weaving.DebugLog;

/**
 * Created by danxx on 2018/1/12.
 * 图片裁剪工具类
 */

public class BitmapClipUtils {

    /**
     * 按竖长方形裁切图片
     *
     * @param bitmap
     * @return
     */
    @DebugLog
    public static Bitmap clipBitmapRect(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();

        Log.d("danxx","before w : "+w);
        Log.d("danxx","before h : "+h);

        int nw, nh, retX, retY;
        if (w > h) {  //横长方形
            nw = h / 2;
            nh = h;
            retX = (w - nw) / 2;
            retY = 0;
        } else { //竖长方形
            nw = w / 2;
            nh = w;
            retX = w / 4;
            retY = (h - w) / 2;
        }

        // 下面这句是关键
        Bitmap bmp = Bitmap.createBitmap(bitmap, retX, retY, nw, nh, null,false);

        Log.d("danxx","after w : "+bmp.getWidth());
        Log.d("danxx","after h : "+bmp.getHeight());

        if (bitmap != null && !bitmap.equals(bmp) && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        return bmp;
    }


}

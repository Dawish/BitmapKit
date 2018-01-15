package danxx.bitmapkit;

import android.content.Context;

import com.glidebitmappool.GlideBitmapPool;

import danxx.bitmapkit.blur.BlurKit;

/**
 * Created by danxx on 2018/1/15.
 */

public class BitmapKitConfig {

    private static final int BITMAP_POOL_MAX_SIZE = 2 * 1024 * 1024;

    /**
     * 工具箱初始化
     * @param context
     * @param bitmapPoolMaxSize
     */
    public static void initialize(Context context, int bitmapPoolMaxSize) {

        /**
         * 模糊工具初始化
         */
        BlurKit.init(context);

        if(bitmapPoolMaxSize < BITMAP_POOL_MAX_SIZE){
            bitmapPoolMaxSize = BITMAP_POOL_MAX_SIZE;
        }

        /**
         * bitmap 复用池最大量
         */
        GlideBitmapPool.initialize(bitmapPoolMaxSize);

    }

}

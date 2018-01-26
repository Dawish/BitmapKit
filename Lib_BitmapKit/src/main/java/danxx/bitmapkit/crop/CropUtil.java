package danxx.bitmapkit.crop;

import android.graphics.Bitmap;
import android.util.Log;

import com.glidebitmappool.GlideBitmapPool;
import com.glidebitmappool.internal.BitmapPool;

import hugo.weaving.DebugLog;

/**
 * Created by danxx on 2018/1/16.
 * Bitmap裁剪工具
 */

public class CropUtil {

    /**
     * 裁剪一定高度保留下面
     * @param srcBitmap
     * @param needHeight
     * @param recycleSrc 是否回收原Bitmap
     * @return
     */
    @DebugLog
    public static Bitmap cropBitmapBottom(Bitmap srcBitmap, int needHeight) {

        Log.d("danxx", "cropBitmapBottom before h : "+srcBitmap.getHeight());

        /**裁剪保留下部分的第一个像素的Y坐标*/
        int needY = srcBitmap.getHeight() - needHeight;

        /**裁剪关键步骤*/
        Bitmap cropBitmap = Bitmap.createBitmap(srcBitmap,0,needY,srcBitmap.getWidth(),needHeight);

        Log.d("danxx", "cropBitmapBottom after h : "+cropBitmap.getHeight());

        /**回收之前的Bitmap*/
        if (srcBitmap != null && !srcBitmap.equals(cropBitmap) && !srcBitmap.isRecycled()) {
//            srcBitmap.recycle();
            GlideBitmapPool.putBitmap(srcBitmap);
        }

        return cropBitmap;
    }


    /**
     * 裁剪一定高度保留下面
     * @param srcBitmap
     * @param needHeight
     * @return
     */
    @DebugLog
    public static Bitmap cropBitmapTop(Bitmap srcBitmap, int needHeight) {

        Log.d("danxx", "cropBitmapBottom before h : "+srcBitmap.getHeight());

        /**裁剪保留上部分的第一个像素的Y坐标*/
        int needY = 0;

        /**裁剪关键步骤*/
        Bitmap cropBitmap = Bitmap.createBitmap(srcBitmap,0,needY,srcBitmap.getWidth(),needHeight);

        Log.d("danxx", "cropBitmapBottom after h : "+cropBitmap.getHeight());

        /**回收之前的Bitmap*/
        if (srcBitmap != null && !srcBitmap.equals(cropBitmap) && !srcBitmap.isRecycled()) {
            srcBitmap.recycle();
        }

        return cropBitmap;
    }

    /**
     * 裁剪一定高度保留左边
     * @param srcBitmap
     * @param needWidth
     * @return
     */
    @DebugLog
    public static Bitmap cropBitmapLeft(Bitmap srcBitmap, int needWidth) {

        Log.d("danxx", "cropBitmapLeft before w : "+srcBitmap.getWidth());

        /**裁剪关键步骤*/
        Bitmap cropBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, needWidth, srcBitmap.getHeight());

        Log.d("danxx", "cropBitmapLeft after w : "+cropBitmap.getWidth());

        /**回收之前的Bitmap*/
        if (srcBitmap != null && !srcBitmap.equals(cropBitmap) && !srcBitmap.isRecycled()) {
            srcBitmap.recycle();
        }

        return cropBitmap;
    }

    /**
     * 裁剪一定高度保留左边
     * @param srcBitmap
     * @param needWidth
     * @return
     */
    @DebugLog
    public static Bitmap cropBitmapRight(Bitmap srcBitmap, int needWidth) {

        Log.d("danxx", "cropBitmapRight before w : "+srcBitmap.getWidth());

        int needX = srcBitmap.getWidth() - needWidth;

        /**裁剪关键步骤*/
        Bitmap cropBitmap = Bitmap.createBitmap(srcBitmap, needX, 0, needWidth, srcBitmap.getHeight());

        Log.d("danxx", "cropBitmapRight after w : "+cropBitmap.getWidth());

        /**回收之前的Bitmap*/
        if (srcBitmap != null && !srcBitmap.equals(cropBitmap) && !srcBitmap.isRecycled()) {
            srcBitmap.recycle();
        }

        return cropBitmap;
    }

}

package danxx.bitmapkit.crop;

import android.graphics.Bitmap;
import android.util.Log;

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
     * @return
     */
    @DebugLog
    private static Bitmap cropBitmapBottom(Bitmap srcBitmap, int needHeight) {

        Log.d("danxx", "cropBitmapBottom before h : "+srcBitmap.getHeight());

        /**裁剪保留下部分的第一个像素的Y坐标*/
        int needY = srcBitmap.getHeight() - needHeight;

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
     * 裁剪一定高度保留下面
     * @param srcBitmap
     * @param needHeight
     * @return
     */
    @DebugLog
    private static Bitmap cropBitmapTop(Bitmap srcBitmap, int needHeight) {

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

}

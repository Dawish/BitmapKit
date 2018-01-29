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

public class BitmapCropUtil {

    /**
     * 默认回收原图的裁剪
     * @param srcBitmap
     * @param needHeight
     * @return
     */
    public static Bitmap cropBitmapBottom(Bitmap srcBitmap, int needHeight){
        return  cropBitmapBottom(srcBitmap,needHeight,true);
    }
    /**
     * 裁剪一定高度保留下面
     * @param srcBitmap
     * @param needHeight
     * @param recycleSrc 是否回收原图
     * @return
     */
    @DebugLog
    public static Bitmap cropBitmapBottom(Bitmap srcBitmap, int needHeight, boolean recycleSrc) {

        Log.d("danxx", "cropBitmapBottom before h : "+srcBitmap.getHeight());

        /**裁剪保留下部分的第一个像素的Y坐标*/
        int needY = srcBitmap.getHeight() - needHeight;

        /**裁剪关键步骤*/
        Bitmap cropBitmap = Bitmap.createBitmap(srcBitmap,0,needY,srcBitmap.getWidth(),needHeight);

        Log.d("danxx", "cropBitmapBottom after h : "+cropBitmap.getHeight());

        /**回收之前的Bitmap*/
        if (recycleSrc && srcBitmap != null && !srcBitmap.equals(cropBitmap) && !srcBitmap.isRecycled()) {
            GlideBitmapPool.putBitmap(srcBitmap);
        }

        return cropBitmap;
    }


    /**
     * 默认回收原图的裁剪
     * @param srcBitmap
     * @param needHeight
     * @return
     */
    public static Bitmap cropBitmapTop(Bitmap srcBitmap, int needHeight) {
        return cropBitmapTop(srcBitmap, needHeight, true);
    }
    /**
     * 裁剪一定高度保留下面
     * @param srcBitmap
     * @param needHeight
     * @param recycleSrc 是否回收原图
     * @return
     */
    @DebugLog
    public static Bitmap cropBitmapTop(Bitmap srcBitmap, int needHeight, boolean recycleSrc) {

        Log.d("danxx", "cropBitmapBottom before h : "+srcBitmap.getHeight());

        /**裁剪保留上部分的第一个像素的Y坐标*/
        int needY = 0;

        /**裁剪关键步骤*/
        Bitmap cropBitmap = Bitmap.createBitmap(srcBitmap,0,needY,srcBitmap.getWidth(),needHeight);

        Log.d("danxx", "cropBitmapBottom after h : "+cropBitmap.getHeight());

        /**回收之前的Bitmap*/
        if (recycleSrc && srcBitmap != null && !srcBitmap.equals(cropBitmap) && !srcBitmap.isRecycled()) {
            GlideBitmapPool.putBitmap(srcBitmap);
        }

        return cropBitmap;
    }


    public static Bitmap cropBitmapLeft(Bitmap srcBitmap, int needWidth) {
        return cropBitmapLeft(srcBitmap,needWidth,true);
    }
    /**
     * 裁剪一定高度保留左边
     * @param srcBitmap
     * @param needWidth
     * @return
     */
    @DebugLog
    public static Bitmap cropBitmapLeft(Bitmap srcBitmap, int needWidth, boolean recycleSrc) {

        Log.d("danxx", "cropBitmapLeft before w : "+srcBitmap.getWidth());

        /**裁剪关键步骤*/
        Bitmap cropBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, needWidth, srcBitmap.getHeight());

        Log.d("danxx", "cropBitmapLeft after w : "+cropBitmap.getWidth());

        /**回收之前的Bitmap*/
        if (recycleSrc && srcBitmap != null && !srcBitmap.equals(cropBitmap) && !srcBitmap.isRecycled()) {
            GlideBitmapPool.putBitmap(srcBitmap);
        }

        return cropBitmap;
    }


    public static Bitmap cropBitmapRight(Bitmap srcBitmap, int needWidth) {
        return cropBitmapRight(srcBitmap, needWidth, true);
    }
    /**
     * 裁剪一定高度保留左边
     * @param srcBitmap
     * @param needWidth
     * @return
     */
    @DebugLog
    public static Bitmap cropBitmapRight(Bitmap srcBitmap, int needWidth, boolean recycleSrc) {

        Log.d("danxx", "cropBitmapRight before w : "+srcBitmap.getWidth());

        int needX = srcBitmap.getWidth() - needWidth;

        /**裁剪关键步骤*/
        Bitmap cropBitmap = Bitmap.createBitmap(srcBitmap, needX, 0, needWidth, srcBitmap.getHeight());

        Log.d("danxx", "cropBitmapRight after w : "+cropBitmap.getWidth());

        /**回收之前的Bitmap*/
        if (recycleSrc && srcBitmap != null && !srcBitmap.equals(cropBitmap) && !srcBitmap.isRecycled()) {
            GlideBitmapPool.putBitmap(srcBitmap);
        }

        return cropBitmap;
    }

    /**
     * 默认回收 随意裁剪
     * @param srcBitmap
     * @param firstPixelX
     * @param firstPixelY
     * @param needWidth
     * @param needHeight
     * @return
     */
    public static Bitmap cropBitmapCustom(Bitmap srcBitmap, int firstPixelX, int firstPixelY, int needWidth, int needHeight) {
        return cropBitmapCustom(srcBitmap, firstPixelX, firstPixelY, needWidth, needHeight, true);
    }

    /**
     * 自定义裁剪，根据第一个像素点(左上角)X和Y轴坐标和需要的宽高来裁剪
     * @param srcBitmap
     * @param firstPixelX
     * @param firstPixelY
     * @param needWidth
     * @param needHeight
     * @param recycleSrc
     * @return
     */
    @DebugLog
    public static Bitmap cropBitmapCustom(Bitmap srcBitmap, int firstPixelX, int firstPixelY, int needWidth, int needHeight, boolean recycleSrc) {

        Log.d("danxx", "cropBitmapRight before w : "+srcBitmap.getWidth());
        Log.d("danxx", "cropBitmapRight before h : "+srcBitmap.getHeight());

        if(firstPixelX + needWidth > srcBitmap.getWidth()){
            needWidth = srcBitmap.getWidth() - firstPixelX;
        }

        if(firstPixelY + needHeight > srcBitmap.getHeight()){
            needHeight = srcBitmap.getHeight() - firstPixelY;
        }

        /**裁剪关键步骤*/
        Bitmap cropBitmap = Bitmap.createBitmap(srcBitmap, firstPixelX, firstPixelY, needWidth, needHeight);

        Log.d("danxx", "cropBitmapRight after w : "+cropBitmap.getWidth());
        Log.d("danxx", "cropBitmapRight after h : "+cropBitmap.getHeight());


        /**回收之前的Bitmap*/
        if (recycleSrc && srcBitmap != null && !srcBitmap.equals(cropBitmap) && !srcBitmap.isRecycled()) {
            GlideBitmapPool.putBitmap(srcBitmap);
        }

        return cropBitmap;
    }

}

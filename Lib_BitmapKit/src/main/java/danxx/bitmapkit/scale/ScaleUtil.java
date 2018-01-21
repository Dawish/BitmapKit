package danxx.bitmapkit.scale;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.glidebitmappool.GlideBitmapPool;
import com.glidebitmappool.internal.BitmapPool;

/**
 * Created by danxx on 2018/1/16.
 * Bitmap缩放工具
 */

public class ScaleUtil {

    /**
     * 按新的宽高缩放图片
     *
     * @param srcBitmap
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap scaleBitmap(Bitmap srcBitmap, int newWidth, int newHeight, boolean recycleSrc) {
        if (srcBitmap == null) {
            return null;
        }
        int width = srcBitmap.getWidth();
        int height = srcBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbm = Bitmap.createBitmap(srcBitmap, 0, 0, width, height, matrix,true);
        if (recycleSrc && srcBitmap != null & !srcBitmap.isRecycled()) {
            GlideBitmapPool.putBitmap(srcBitmap);
        }
        return newbm;
    }
    /**
     * 根据指定的宽度比例值和高度比例值进行缩放
     * @param srcBitmap
     * @param scaleWidth
     * @param scaleHeight
     * @param recycleSrc 是否回收Bitmap
     * @return
     */
    public static Bitmap scaleBitmap(Bitmap srcBitmap, float scaleWidth, float scaleHeight, boolean recycleSrc) {
        int width = srcBitmap.getWidth();
        int height = srcBitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(srcBitmap, 0, 0, width, height, matrix, true);
        if (bitmap != null) {
            /**回收*/
            if (recycleSrc && srcBitmap != null && !srcBitmap.equals(bitmap) && !srcBitmap.isRecycled()) {
                GlideBitmapPool.putBitmap(srcBitmap);
            }
            return bitmap;
        } else {
            return srcBitmap;
        }
    }
}

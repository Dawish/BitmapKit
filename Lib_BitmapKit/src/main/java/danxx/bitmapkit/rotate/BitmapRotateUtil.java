package danxx.bitmapkit.rotate;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.glidebitmappool.GlideBitmapPool;

/**
 * Created by danxx on 2018/1/28.
 * @Desc Bitmap旋转工具
 */

public class BitmapRotateUtil {

    /**
     * 默认回收
     * @param srcBitmap
     * @param degrees
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap srcBitmap, float degrees) {
        return rotateBitmap(srcBitmap, degrees, true);
    }

    /**
     * 选择变换
     *
     * @param srcBitmap 原图
     * @param degrees  旋转角度，可正可负
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmap(Bitmap srcBitmap, float degrees, boolean recycleSrc) {
        if (srcBitmap == null) {
            return null;
        }
        int width = srcBitmap.getWidth();
        int height = srcBitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(srcBitmap, 0, 0, width, height, matrix, true);
        if (recycleSrc && srcBitmap != null & !srcBitmap.isRecycled() && !srcBitmap.equals(newBM)) {
            GlideBitmapPool.putBitmap(srcBitmap);
        }
        return newBM;
    }

}

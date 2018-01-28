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
     * 选择变换
     *
     * @param srcBitmap 原图
     * @param degrees  旋转角度，可正可负
     * @return 旋转后的图片
     */
    private Bitmap rotateBitmap(Bitmap srcBitmap, float degrees, boolean recycleSrc) {
        if (srcBitmap == null) {
            return null;
        }
        int width = srcBitmap.getWidth();
        int height = srcBitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(srcBitmap, 0, 0, width, height, matrix, false);
        if (recycleSrc && srcBitmap != null & !srcBitmap.isRecycled()) {
            GlideBitmapPool.putBitmap(srcBitmap);
        }
        return newBM;
    }

}

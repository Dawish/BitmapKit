package danxx.bitmapkit.blur;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import com.glidebitmappool.GlideBitmapPool;

import java.lang.reflect.Field;

import danxx.bitmapkit.scale.BitmapScaleUtil;
import hugo.weaving.DebugLog;

/**
 * @author danxx
 * @date 2018/1/12
 * @desc 阴影圆角工具
 */

public class ShadeUtil {

    /**
     * 给缩小的原图四周加上渐变的阴影
     *
     * @param srcBitmap
     * @param edgeSrcDrawable
     * @param create          阴影模糊图片是否创建新的
     * @return
     */
    @DebugLog
    public static Bitmap createShadeBitmap(Canvas canvas, Bitmap srcBitmap, int shaderPadding, Drawable edgeSrcDrawable, Rect currentRect, boolean create) {

        Paint paint = new Paint();

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));

        paint.setDither(true);
        Bitmap blurBitmap = null;

        if (create) {

            Log.d("danxxx", "create : " + create);

            Bitmap bitmap = GlideBitmapPool.getBitmap(currentRect.width(), currentRect.height(), Bitmap.Config.ARGB_8888);

            Canvas drawCanvas = new Canvas(bitmap);

            Rect rect = new Rect(currentRect);
            //四周留出px画阴影
            rect.inset(currentRect.width()/6, currentRect.height()/6);
            //画图片
            drawCanvas.drawBitmap(srcBitmap, null, rect, paint);
            //画阴影圈
//            drawDrawableAt(drawCanvas, rect, edgeSrcDrawable);
            //模糊前缩小
            bitmap = BitmapScaleUtil.scaleBitmap(bitmap, 0.2f, 0.2f, true);
            //开始模糊
            blurBitmap = BlurKit.getInstance().blur(bitmap, 16);
            //模糊后放大
            blurBitmap = BitmapScaleUtil.scaleBitmap(blurBitmap, 5.0f, 5.0f, true);

        } else {
            Log.d("danxxx", "create : " + create);
            blurBitmap = srcBitmap;
        }

        if (blurBitmap != null) {
            //下移
//            currentRect.offset(0, currentRect.height() / 4);
//            //左右放大
//            currentRect.inset(-currentRect.height() / 5, 0);
            canvas.drawBitmap(blurBitmap, null, currentRect, paint);
        }

        return blurBitmap;
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = getMetrics(context.getResources()).density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static float pt2px(Context context, float pt) {
        return TypedValue.applyDimension(3, pt, getMetrics(context.getResources()));
    }

    private static DisplayMetrics getMetrics(Resources res) {
        DisplayMetrics dm = null;
        if ("MiuiResources".equals(res.getClass().getSimpleName()) || "XResources".equals(res.getClass().getSimpleName())) {
            try {
                Field field = Resources.class.getDeclaredField("mTmpMetrics");
                field.setAccessible(true);
                dm = (DisplayMetrics) field.get(res);
            } catch (Exception var3) {
                dm = null;
            }
        }

        return dm != null ? dm : res.getDisplayMetrics();
    }

    public static void scaleRect(Rect rect, float ratioX, float ratioY) {
        if (null != rect) {
            int offsetX = (int) ((float) (rect.right - rect.left) * (ratioX - 1.0F) / 2.0F);
            int offsetY = (int) ((float) (rect.bottom - rect.top) * (ratioY - 1.0F) / 2.0F);
            rect.left -= offsetX;
            rect.right += offsetX;
            rect.top -= offsetY;
            rect.bottom += offsetY;
        }
    }

    @DebugLog
    public static void drawDrawableAt(Canvas canvas, Rect position, Drawable drawable) {
        if (null != canvas && null != position && null != drawable) {
            Rect padding = new Rect();
            drawable.getPadding(padding);
            Rect bounds = new Rect();
            bounds.left = position.left - padding.left;
            bounds.top = position.top - padding.top;
            bounds.right = position.right + padding.right;
            bounds.bottom = position.bottom + padding.bottom;
            drawable.setBounds(bounds);
            drawable.draw(canvas);
        }
    }

}

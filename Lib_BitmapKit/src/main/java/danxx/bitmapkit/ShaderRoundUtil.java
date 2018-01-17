package danxx.bitmapkit;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.Log;

import com.glidebitmappool.GlideBitmapPool;

import danxx.bitmapkit.blur.BlurKit;
import hugo.weaving.DebugLog;

/**
 * @author danxx
 * @date 2018/1/12
 * @desc 阴影圆角工具
 */

public class ShaderRoundUtil {

  /**
   *
   * @param canvas 当前view的画布
   * @param bitmap 阴影处理图片
   * @param mRadius 圆角半径
   * @param currentRect 当前view的绘制矩形
   * @param shadowHeight 下面要绘制阴影的高度
   * @return
   */
  @DebugLog
  public static void drawRoundBlurShader(Canvas canvas, Bitmap bitmap, int mRadius,Rect currentRect,int shadowHeight){
    Bitmap rbsBitmap = null;
    if(bitmap == null){
      return;
    }

    Paint paint = new Paint();
//    paint.setAlpha(30);
    /**把图片裁剪成阴影高度*/
    rbsBitmap = clipBitmapBottom(bitmap,shadowHeight,0.6f);

    rbsBitmap = BlurKit.getInstance().blur(rbsBitmap, 18);

    RectF shadowRect = new RectF(currentRect);
    shadowRect.bottom = shadowHeight;

    shadowRect.offset(0,(currentRect.height() - (shadowHeight/2)));

    /**渐变Bitmap*/
    BitmapShader bitmapShader = new BitmapShader(rbsBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

    LinearGradient linearGradient = new LinearGradient(0,shadowRect.top,0,shadowRect.bottom, Color.BLACK, Color.TRANSPARENT,Shader.TileMode.CLAMP);

    ComposeShader composeShader = new ComposeShader(bitmapShader,linearGradient,new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

    paint.setShader(composeShader);

    /**绘制圆角矩形*/
    canvas.drawRoundRect(shadowRect ,mRadius ,mRadius ,paint);

  }


  /**
   * 返回一个抠图模糊渐变圆角图片
   * @param srcBitmap 阴影处理图片
   * @param mRadius 圆角半径
   * @param currentRect 当前view的绘制矩形
   * @param shadowHeight 下面要绘制阴影的高度
   * @return
   */
  @DebugLog
  public static Bitmap processRoundBlurShader(Canvas canvas,Bitmap srcBitmap, int mRadius,Rect currentRect,int shadowHeight){
    Bitmap rbsBitmap = null;
    if(srcBitmap == null){
      Log.d("danxx", "return rbsBitmap == null");
      return srcBitmap;
    }
    Log.d("danxx", "processRoundBlurShader...");


    Paint paint = new Paint();
    //取消透明
    //paint.setAlpha(30);
    /**把图片裁剪成阴影高度*/
    rbsBitmap = clipBitmapBottom(srcBitmap,shadowHeight,0.6f);

    rbsBitmap = BlurKit.getInstance().blur(rbsBitmap, 18);

    RectF shadowRect = new RectF(currentRect);
    shadowRect.bottom = shadowHeight;
    //取消偏移
    //shadowRect.offset(0,(currentRect.height() - (shadowHeight/2)));

    /**临时画布*/
    Canvas tempCanvas = new Canvas();
    Bitmap tempBitmap/* = Bitmap.createBitmap((int)shadowRect.width(), (int)shadowRect.height(), Bitmap.Config.ARGB_8888)*/;
    tempBitmap = GlideBitmapPool.getDirtyBitmap((int)shadowRect.width(), (int)shadowRect.height(), Bitmap.Config.ARGB_4444);
    tempCanvas.setBitmap(tempBitmap);

    /**渐变Bitmap*/
    BitmapShader bitmapShader = new BitmapShader(rbsBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    /**线性渐变*/
    LinearGradient linearGradient = new LinearGradient(0,shadowRect.top,0,shadowRect.bottom, Color.BLACK, Color.TRANSPARENT,Shader.TileMode.CLAMP);
    /**混合*/
    ComposeShader composeShader = new ComposeShader(bitmapShader,linearGradient,new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

    paint.setShader(composeShader);

    /**绘制圆角矩形*/
    tempCanvas.drawRoundRect(shadowRect ,mRadius ,mRadius ,paint);

    /**回收之前的Bitmap*/
    if (srcBitmap != null && !srcBitmap.equals(tempBitmap) && !srcBitmap.isRecycled()) {
//      srcBitmap.recycle();
      GlideBitmapPool.putBitmap(srcBitmap);
    }
    return tempBitmap;
  }

  /**
   * 裁剪一定高度保留下面
   * @param srcBitmap
   * @param needHeight
   * @return
   */
  @DebugLog
  private static Bitmap clipBitmapBottom(Bitmap srcBitmap, int needHeight) {

    Log.d("danxx", "clipBitmapBottom before h : "+srcBitmap.getHeight());

    /**裁剪保留下部分的第一个像素的Y坐标*/
    int needY = srcBitmap.getHeight() - needHeight;

    /**裁剪关键步骤*/
    Bitmap clipBitmap = Bitmap.createBitmap(srcBitmap,0,needY,srcBitmap.getWidth(),needHeight);

    Log.d("danxx", "clipBitmapBottom after h : "+clipBitmap.getHeight());

    /**回收之前的Bitmap*/
    if (srcBitmap != null && !srcBitmap.equals(clipBitmap) && !srcBitmap.isRecycled()) {
//      srcBitmap.recycle();
      GlideBitmapPool.putBitmap(srcBitmap);
    }

    return clipBitmap;
  }

  /**
   * 裁剪一定高度并缩放保留下面
   * @param srcBitmap 原图
   * @param needHeight 裁剪后需要的高度
   * @param scaleValue 裁剪后的缩放
   * @return
   */
  @DebugLog
  private static Bitmap clipBitmapBottom(Bitmap srcBitmap, int needHeight, float scaleValue) {

    Log.d("danxx", "clipBitmapBottom before h : "+srcBitmap.getHeight());

    /**裁剪保留下部分的第一个像素的Y坐标*/
    int needY = srcBitmap.getHeight() - needHeight;

    /**缩放矩阵*/
    Matrix scaleMatrix = new Matrix();
    scaleMatrix.postScale(scaleValue, scaleValue);

    /**裁剪关键步骤*/
    Bitmap clipBitmap = Bitmap.createBitmap(srcBitmap,0,needY,srcBitmap.getWidth(),needHeight, scaleMatrix, true);

    Log.d("danxx", "clipBitmapBottom after h : "+clipBitmap.getHeight());

    /**回收之前的Bitmap*/
    if (srcBitmap != null && !srcBitmap.equals(clipBitmap) && !srcBitmap.isRecycled()) {
//      srcBitmap.recycle();
      GlideBitmapPool.putBitmap(srcBitmap);
    }

    return clipBitmap;
  }

  /**
   * Bitmap绘制成圆角返回
   * @param bitmap
   * @param mRadius
   * @return
   */
  private Bitmap createRoundBitmap(Bitmap bitmap, int mRadius){
    if(bitmap == null){
      return null;
    }

    int mWidth = bitmap.getWidth();
    int mHeight = bitmap.getHeight();

    final Paint paint = new Paint();
    /**开启抗锯齿**/
    paint.setAntiAlias(true);
    /****/
    Bitmap target  = Bitmap.createBitmap(mWidth,mHeight, Bitmap.Config.ARGB_8888);
    /**
     * Construct a canvas with the specified bitmap to draw into. The       bitmapmust be mutable
     * 以bitmap对象创建一个画布，则将内容都绘制在bitmap上，bitmap不得为null;
     */
    Canvas canvas = new Canvas(target);
    /**新建一个矩形绘制区域,并给出左上角和右下角的坐标**/
    RectF rect = new RectF(0 , 0 ,mWidth ,mHeight);
    /**
     * 把图片缩放成我们想要的大小
     */
    bitmap = Bitmap.createScaledBitmap(bitmap,mWidth,mHeight,false);
    /**在绘制矩形区域绘制用画笔绘制一个圆角矩形**/
    canvas.drawRoundRect(rect ,mRadius ,mRadius ,paint);
    /**
     * 我简单理解为设置画笔在绘制时图形堆叠时候的显示模式
     * SRC_IN:取两层绘制交集。显示上层。
     */
    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    canvas.drawBitmap(bitmap ,0 ,0 ,paint);

    /**回收之前的Bitmap*/
    if (bitmap != null && !bitmap.equals(target) && !bitmap.isRecycled()) {
//      bitmap.recycle();
      GlideBitmapPool.putBitmap(bitmap);
    }

    /****/
    return target;
  }

}

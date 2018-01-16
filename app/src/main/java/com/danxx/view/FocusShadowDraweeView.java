package com.danxx.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import com.facebook.drawee.generic.RootDrawable;
import com.facebook.drawee.view.SimpleDraweeView;
import danxx.bitmapkit.ShaderRoundUtils;
import hugo.weaving.DebugLog;

/**
 * @author danxx
 * @date 2018/1/12
 * @desc
 */

public class FocusShadowDraweeView extends SimpleDraweeView {

  private Bitmap blurBitmap;
  private Rect currentRect;
  private RectF edgeRectF;
  private Paint paint;
  private int mRadius;

  public FocusShadowDraweeView(Context context) {
    this(context, null);
  }

  public FocusShadowDraweeView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public FocusShadowDraweeView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  private void init(){
    Log.i("danxx", "ShadowDraweeView init");
    currentRect = new Rect();
    edgeRectF = new RectF();
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(Color.WHITE);
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeWidth(4);
    mRadius = 15;
  }

  @DebugLog
  @Override
  protected void onDraw(Canvas canvas) {
    if(blurBitmap == null){
      if(hasFocus()){
        getDrawingRect(currentRect);
        Drawable drawable = null;
        Drawable background = getBackground();
        if (background == null) {
          Drawable draw = ((RootDrawable) getDrawable()).getDrawable();
          if (draw != null) {
            drawable = draw;
          }
        } else {
          drawable = background;
        }
        if(drawable != null){
          blurBitmap = drawable2bitmap(drawable);
        }
        if(blurBitmap != null){
          Log.i("danxx", "to drawRoundBlurShader");
          blurBitmap = ShaderRoundUtils.processRoundBlurShader(blurBitmap,mRadius,currentRect,80, true);
          ShaderRoundUtils.drawRoundBlurShader(canvas, blurBitmap, currentRect);
        }else {
          Log.i("danxx", "getDrawingCache == null");
        }
      }

      super.onDraw(canvas);

      if(hasFocus()){
        getDrawingRect(currentRect);
        edgeRectF.set(currentRect);
        canvas.drawRoundRect(edgeRectF, 15,15,paint);
      }
    }else {
      if(hasFocus()){
        getDrawingRect(currentRect);
        ShaderRoundUtils.drawRoundBlurShader(canvas, blurBitmap, currentRect);
      }

      super.onDraw(canvas);

      if(hasFocus()){
        getDrawingRect(currentRect);
        edgeRectF.set(currentRect);
        canvas.drawRoundRect(edgeRectF, 15,15,paint);
      }
    }
  }
  private Bitmap drawable2bitmap(Drawable drawable) {
    Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
            : Bitmap.Config.RGB_565);
    Canvas canvas = new Canvas(bitmap);
    drawable.setBounds(0, 0, getWidth(), getHeight());
    drawable.draw(canvas);
    return bitmap;
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    if (blurBitmap != null && !blurBitmap.isRecycled()) {
      blurBitmap.recycle();
    }
  }

  @Override
  protected void onFocusChanged(boolean gainFocus, int direction,
      @Nullable Rect previouslyFocusedRect) {
    super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    if(hasFocus()){
      ViewCompat.animate(this).scaleX(1.1f).scaleY(1.1f).setDuration(300).start();
    }else {
      ViewCompat.animate(this).scaleX(1.0f).scaleY(1.0f).setDuration(300).start();
    }
  }
}

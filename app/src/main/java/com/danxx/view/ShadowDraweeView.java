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

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RootDrawable;
import com.facebook.drawee.view.SimpleDraweeView;

import danxx.bitmapkit.ShaderRoundUtils;
import hugo.weaving.DebugLog;

/**
 * @author danxx
 * @date 2018/1/12
 * @desc
 */

public class ShadowDraweeView extends SimpleDraweeView {

    private Bitmap blurBitmap;
    private Rect currentRect;
    private Paint paint;
    private int mRadius;

    public ShadowDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        init();
    }

    public ShadowDraweeView(Context context) {
        super(context);
        init();
    }

    public ShadowDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShadowDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ShadowDraweeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        Log.i("danxx", "ShadowDraweeView init");
        currentRect = new Rect();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        mRadius = 10;
    }

    @DebugLog
    @Override
    protected void onDraw(Canvas canvas) {
        if (blurBitmap == null) {
            if (isSelected()) {
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
                if (drawable != null) {
                    blurBitmap = drawable2bitmap(drawable);
                }
                if (blurBitmap != null && !blurBitmap.isRecycled()) {
                    Log.i("danxx", "to drawRoundBlurShader");
                    blurBitmap = ShaderRoundUtils.processRoundBlurShader(blurBitmap, mRadius, currentRect, 80);
                    ShaderRoundUtils.drawRoundBlurShader(canvas, blurBitmap, currentRect);
                } else {
                    Log.i("danxx", "getDrawingCache == null");
                }
            }
            super.onDraw(canvas);

        } else {
            if (isSelected() && !blurBitmap.isRecycled()) {
                getDrawingRect(currentRect);
                ShaderRoundUtils.drawRoundBlurShader(canvas, blurBitmap, currentRect);
            }
            super.onDraw(canvas);

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
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            ViewCompat.animate(this).scaleX(1.02f).scaleY(1.02f).setDuration(300).start();
        } else {
            ViewCompat.animate(this).scaleX(1.0f).scaleY(1.0f).setDuration(300).start();
        }
    }

}

package com.danxx.demoview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.danxx.R;

import danxx.bitmapkit.blur.ShadeUtil;

/**
 * Created by danxx on 2018/1/29.
 *
 * @Desc Bitmap高斯模糊示例控件
 */

public class BlurImageView extends View {

    Bitmap bitmap;
    Paint paint;
    private Drawable shaderDrawable;
    private Rect currentRect;
    private int shaderPadding = ShadeUtil.dip2px(BlurImageView.this.getContext(), 19);

    public BlurImageView(Context context) {
        this(context, null);
    }

    public BlurImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlurImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        final TypedArray a =
                context.obtainStyledAttributes(attrs, R.styleable.DemoImageView, defStyleAttr, 0);

        int bitmapId = a.getResourceId(R.styleable.DemoImageView_bitmapSrc, R.drawable.gaoyuanyuan);

        bitmap = BitmapFactory.decodeResource(getResources(), bitmapId);

        shaderDrawable = getResources().getDrawable(R.drawable.shadow_5);

        currentRect = new Rect();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        getDrawingRect(currentRect);
        super.onDraw(canvas);

        bitmap = ShadeUtil.createShadeBitmap(canvas, bitmap, shaderPadding, shaderDrawable, currentRect, true);
    }
}

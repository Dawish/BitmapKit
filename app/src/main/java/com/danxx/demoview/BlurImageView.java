package com.danxx.demoview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.danxx.R;

/**
 * Created by danxx on 2018/1/29.
 * @Desc Bitmap高斯模糊示例控件
 */

public class BlurImageView extends View {

    Bitmap bitmap;
    Paint paint;

    public BlurImageView(Context context) {
        this(context, null);
    }

    public BlurImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlurImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){

        final TypedArray a =
                context.obtainStyledAttributes(attrs, R.styleable.DemoImageView, defStyleAttr, 0);

        int bitmapId = a.getResourceId(R.styleable.DemoImageView_bitmapSrc,R.drawable.gaoyuanyuan);

        bitmap = BitmapFactory.decodeResource(getResources(), bitmapId);

    }

}

package com.danxx.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.danxx.R;
import com.danxx.utils.BitmapClipUtils;

import danxx.bitmapkit.ShaderRoundUtil;
import hugo.weaving.DebugLog;

/**
 * Created by danxx on 2018/1/14.
 */

public class RoundBlurShaderView extends View {

    Paint paint = new Paint();
    Bitmap bitmap;
    Rect rect = new Rect();

    public RoundBlurShaderView(Context context) {
        this(context, null);
    }

    public RoundBlurShaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundBlurShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();

    }

    private void init(){

        paint.setAntiAlias(true);

        bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.bitmap_clip);

        if(bitmap != null){
            Log.d("danxx", "BitmapFactory != null");
        }else {
            Log.d("danxx", "BitmapFactory tempBitmap == null");
        }

    }

    int shadowHeight = 180;

    @DebugLog
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        rect.set(0,0,bitmap.getWidth(),bitmap.getHeight());

        bitmap = ShaderRoundUtil.processRoundBlurShader(canvas,bitmap,10,rect,shadowHeight);

        if(bitmap!=null){

            Log.d("danxx", "drawBitmap----->");

            RectF shadowRect = new RectF(rect);
            shadowRect.bottom = shadowHeight;

            canvas.drawBitmap(bitmap,null,shadowRect,paint);
        }

    }
}

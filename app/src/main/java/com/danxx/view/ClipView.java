package com.danxx.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.danxx.R;
import com.danxx.utils.BitmapClipUtils;

import danxx.bitmapkit.ShaderRoundUtil;
import hugo.weaving.DebugLog;

/**
 * @author danxx
 */

public class ClipView extends View {
    public ClipView(Context context) {
        super(context);
        init();
    }

    public ClipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint paint = new Paint();
    Bitmap bitmap;
    Rect rect = new Rect();
    Bitmap temp;

    private void init(){

        paint.setAntiAlias(true);
        bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.bitmap_clip);
        temp = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.bitmap_clip);
    }

    @DebugLog
    @Override
    protected void onDraw(Canvas canvas) {


        rect.set(0,0,bitmap.getWidth(),bitmap.getHeight());
        ShaderRoundUtil.drawRoundBlurShader(canvas,bitmap,10,rect,180);

        canvas.drawBitmap(temp,0,0,paint);


    }
}

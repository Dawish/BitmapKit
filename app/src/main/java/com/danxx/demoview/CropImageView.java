package com.danxx.demoview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.danxx.R;
import danxx.bitmapkit.crop.BitmapCropUtil;

/**
 * Created by danxx on 2018/1/26.
 * Bitmap裁剪示例View
 */

public class CropImageView extends View {

    Bitmap bitmap;
    int needLength;
    Paint paint;

    public CropImageView(Context context) {
        this(context,null);
    }

    public CropImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CropImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){

        final TypedArray a =
                context.obtainStyledAttributes(attrs, R.styleable.DemoImageView, defStyleAttr, 0);

         int bitmapId = a.getResourceId(R.styleable.DemoImageView_bitmapSrc,R.drawable.gaoyuanyuan);

         int cropType = a.getInteger(R.styleable.DemoImageView_bitmapCropType,1);

         bitmap = BitmapFactory.decodeResource(getResources(), bitmapId);

         paint = new Paint();

         switch (cropType){
             case 1:
                 needLength = bitmap.getWidth()/2;
                 bitmap = BitmapCropUtil.cropBitmapLeft(bitmap,needLength);
                 break;
             case 2:
                 needLength = bitmap.getHeight()/2;
                 bitmap = BitmapCropUtil.cropBitmapTop(bitmap,needLength);
                 break;
             case 3:
                 needLength = bitmap.getHeight()/2;
                 bitmap = BitmapCropUtil.cropBitmapBottom(bitmap,needLength);
                 break;
             case 4:
                 needLength = bitmap.getWidth()/2;
                 bitmap = BitmapCropUtil.cropBitmapRight(bitmap,needLength);
                 break;
             case 5:
                 int needW = bitmap.getWidth()/2;
                 int needH = bitmap.getHeight()/2;
                 bitmap = BitmapCropUtil.cropBitmapCustom(bitmap,140, 80,needW, needH);
                 break;
         }

        a.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bitmap.getWidth(),bitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(bitmap!=null){
            canvas.drawBitmap(bitmap,0,0,paint);
        }
    }
}

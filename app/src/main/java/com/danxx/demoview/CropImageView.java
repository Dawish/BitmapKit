package com.danxx.demoview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.danxx.R;
import com.danxx.utils.BitmapClipUtils;

import danxx.bitmapkit.crop.CropUtil;

/**
 * Created by danxx on 2018/1/26.
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
                context.obtainStyledAttributes(attrs, R.styleable.CropImageView, defStyleAttr, 0);

         int bitmapId = a.getResourceId(R.styleable.CropImageView_bitmapSrc,R.drawable.gaoyuanyuan);

         int cropType = a.getInteger(R.styleable.CropImageView_bitmapCropType,1);

         bitmap = BitmapFactory.decodeResource(getResources(), bitmapId);

         paint = new Paint();

         switch (cropType){
             case 1:
                 needLength = bitmap.getWidth()/2;
                 bitmap = CropUtil.cropBitmapLeft(bitmap,needLength);
                 break;
             case 2:
                 needLength = bitmap.getHeight()/2;
                 bitmap = CropUtil.cropBitmapTop(bitmap,needLength);
                 break;
             case 3:
                 needLength = bitmap.getHeight()/2;
                 bitmap = CropUtil.cropBitmapBottom(bitmap,needLength);
                 break;
             case 4:
                 needLength = bitmap.getWidth()/2;
                 bitmap = CropUtil.cropBitmapRight(bitmap,needLength);
                 break;
             case 5:
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

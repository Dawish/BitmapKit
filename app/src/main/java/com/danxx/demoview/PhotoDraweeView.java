package com.danxx.demoview;

import android.content.Context;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by danxx on 2018/1/28.
 */

public class PhotoDraweeView extends SimpleDraweeView{

    public PhotoDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public PhotoDraweeView(Context context) {
        super(context);
    }

    public PhotoDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhotoDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}

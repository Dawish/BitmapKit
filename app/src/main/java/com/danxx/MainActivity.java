package com.danxx;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearSnapHelper;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import danxx.bitmapkit.blur.BlurKit;

/**
 *  @author danxx
 */
public class MainActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BlurKit.init(MainActivity.this);

        setContentView(R.layout.activity_main);


//        btn = findViewById(R.id.btn);
//
//        btn.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                Button touchedButton = (Button)view;
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        touchedButton.getBackground().setColorFilter(0x22000000, PorterDuff.Mode.SRC_ATOP);
//                        touchedButton.invalidate();
//                        break;
//                    case MotionEvent.ACTION_CANCEL:
//                    case MotionEvent.ACTION_UP:
//                        touchedButton.getBackground().clearColorFilter();
//                        touchedButton.invalidate();
//                        break;
//                }
//                return true;
//
//            }
//        });

    }
}

package com.danxx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;

import com.danxx.utils.BlurKit;

/**
 *  @author danxx
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BlurKit.init(MainActivity.this);

        setContentView(R.layout.activity_main);

    }
}

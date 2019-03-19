package com.pole6lynn.windowdemo;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class DemoActivity_1 extends Activity {
    private static final String TAG = "DemoActivity_1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_1);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Button button = (Button)findViewById(R.id.button1);
            performAnimate(button, button.getWidth(), 500);
        }
    }

    private void performAnimate(final View targe, final int start, final int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            IntEvaluator intEvaluator = new IntEvaluator();
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (int)animation.getAnimatedValue();
                Log.d(TAG, "Current value = " + currentValue);
                float fraction = animation.getAnimatedFraction();
                targe.getLayoutParams().width = intEvaluator.evaluate(fraction, start, end);
                targe.requestLayout();
            }
        });
        valueAnimator.setDuration(500).start();
    }
}

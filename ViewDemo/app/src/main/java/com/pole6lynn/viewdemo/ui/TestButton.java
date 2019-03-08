package com.pole6lynn.viewdemo.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

@SuppressLint("AppCompatCustomView")
public class TestButton extends TextView {
    private static final String TAG = "TextView";

    private int mScaledTouchSlop;
    private int mLastX = 0;
    private int mLastY = 0;

    public TestButton(Context context) {
        super(context);
    }

    public TestButton(Context context, AttributeSet set) {
        super(context, set);
        init();
    }

    public TestButton(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        init();
    }

    private void init() {
        mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        Log.d(TAG, "mScaledTouchSlop = " + mScaledTouchSlop);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                Log.d(TAG, "move , deltaX = " + deltaX + ", deltaY = " + deltaY);
                int translateX = (int)ViewHelper.getTranslationX(this) + deltaX;
                int translateY = (int)ViewHelper.getTranslationY(this) + deltaY;
                ViewHelper.setTranslationX(this, translateX);
                ViewHelper.setTranslationY(this, translateY);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }
}

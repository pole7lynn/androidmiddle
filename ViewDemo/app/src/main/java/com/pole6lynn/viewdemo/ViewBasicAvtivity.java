package com.pole6lynn.viewdemo;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ViewBasicAvtivity extends AppCompatActivity implements View.OnClickListener,
        View.OnLongClickListener {
    private static final String TAG = "ViewBasicAvtivity";
    private Button mBtn1;
    private View mBtn2;

    private static final int MESSAGE_SCROLL_TO = 1;
    private static final int FRAME_COUNT = 30;
    private static final int DELAY_TIME = 33;

    private int mCount = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SCROLL_TO:
                    mCount++;
                    if (mCount <= FRAME_COUNT) {
                        float fraction = mCount / (float) FRAME_COUNT;
                        int scrollX = (int) (fraction * 100);
                        mBtn1.scrollTo(scrollX, 0);
                        mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO, DELAY_TIME);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_basic);
        initView();
    }

    private void initView() {
        mBtn1 = findViewById(R.id.view_basic_button1);
        mBtn2 = findViewById(R.id.test_button);
        mBtn1.setOnClickListener(this);
        mBtn2.setOnLongClickListener(this);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Log.d(TAG, "Btn1.x = " + mBtn1.getX());
            Log.d(TAG, "Btn1.left = " + mBtn1.getLeft());
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mBtn1) {
            mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO, DELAY_TIME);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(this, "Long click", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }
}

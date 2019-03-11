package com.pole6lynn.remoteviewdemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import com.pole6lynn.remoteviewdemo.util.MyConstants;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    private LinearLayout mRemoteViewsContent;
    private BroadcastReceiver mRemoteViewsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            RemoteViews remoteViews = intent.getParcelableExtra(MyConstants.EXTRA_REMOTE_VIEWS);
            if (remoteViews != null) {
                updateUI(remoteViews);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Log.d(TAG, "init view:");
        mRemoteViewsContent = (LinearLayout) findViewById(R.id.remote_views_content);
        IntentFilter filter = new IntentFilter(MyConstants.REMOTE_ACTION);
        registerReceiver(mRemoteViewsReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mRemoteViewsReceiver);
        super.onDestroy();
    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.button1) {
            Intent intent = new Intent(this, TestActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.button2) {
            Intent intent = new Intent(this, Demo2Activity.class);
            startActivity(intent);
        }
    }

    private void updateUI(RemoteViews remoteViews) {

        //The same application.
        /*View view = remoteViews.apply(this, mRemoteViewsContent);
        mRemoteViewsContent.addView(view);*/

        //the different application.
        int layoutId = getResources().getIdentifier("layout_simulated_notification",
                "layout", getPackageName());
        View view = getLayoutInflater().inflate(layoutId, mRemoteViewsContent, false);
        remoteViews.reapply(this, view);
        mRemoteViewsContent.addView(view);
    }
}

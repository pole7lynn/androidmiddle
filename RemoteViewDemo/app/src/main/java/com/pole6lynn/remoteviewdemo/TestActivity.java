package com.pole6lynn.remoteviewdemo;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class TestActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "TestActivity";

    private Button mBtn1;
    private View mBtn2;

    private static int sId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        mBtn1 = findViewById(R.id.test_button1);
        mBtn2 = findViewById(R.id.test_button2);
        mBtn2.setOnClickListener(this);
        mBtn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mBtn1) {
            sId++;
            Log.d(TAG, "Show normal notification. sId = " + sId);
            Intent intent = new Intent(this, Demo2Activity.class);
            intent.putExtra("sid", sId);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationManager manager = (NotificationManager) getSystemService(
                    Context.NOTIFICATION_SERVICE);
            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("Chapter5")
                    .setContentText("Hello Lynn")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
            manager.notify(sId, notification);
        } else if (v == mBtn2) {
            sId ++;
            Intent intent = new Intent(this, Demo2Activity.class);
            intent.putExtra("sid", sId);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            Log.d(TAG, "pendingIntent = " + pendingIntent);
            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
            remoteViews.setTextViewText(R.id.msg, "chapter5" +sId);
            remoteViews.setImageViewResource(R.id.icon, R.drawable.icon1);
            remoteViews.setOnClickPendingIntent(R.id.open_activity2, pendingIntent);
            Notification notification = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setCustomContentView(remoteViews)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
            NotificationManager manager = (NotificationManager) getSystemService(
                    Context.NOTIFICATION_SERVICE);
            manager.notify(sId, notification);
        }
    }
}

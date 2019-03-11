package com.pole6lynn.remoteviewdemo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyAppWidgetProvider extends AppWidgetProvider {
    private static final String TAG = "MyAppWidgetProvider";
    public final String CLICK_ACTION = "com.pole6lynn.remoteviewdemo.CLICK";

    public MyAppWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        super.onReceive(context, intent);
        Log.d(TAG, "Receive action : " + intent.getAction());
        if (intent.getAction() == CLICK_ACTION) {
            Toast.makeText(context, "Click it.", Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap srcbBitmap = BitmapFactory.decodeResource(
                            context.getResources(), R.drawable.icon1);
                    AppWidgetManager manager = AppWidgetManager.getInstance(context);
                    for (int i = 0; i < 37; i++) {
                        float degree = (i * 10) % 360;
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                                R.layout.widget);
                        remoteViews.setImageViewBitmap(R.id.imageView1,
                                rotateBitmap(context, srcbBitmap, degree));
                        Intent intentClick = new Intent();
                        intentClick.setAction(CLICK_ACTION);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                                0, intentClick, 0);
                        remoteViews.setOnClickPendingIntent(R.id.imageView1, pendingIntent);
                        manager.updateAppWidget(new ComponentName(context, MyAppWidgetProvider.class),
                                remoteViews);
                        SystemClock.sleep(30);
                    }
                }
            }).start();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d(TAG, "Update widget.");
        final int counter = appWidgetIds.length;
        Log.i(TAG, "counter = " + counter);
        for (int i = 0; i < counter; i++) {
            int appWidgetId = appWidgetIds[i];
            onWidgetUpdate(context, appWidgetManager, appWidgetId);
        }
    }

    private void onWidgetUpdate(Context context, AppWidgetManager manager, int appWidgetId) {
        Log.i(TAG, "appWidgetId = " + appWidgetId);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget);

        // "窗口小部件"点击事件发送的Intent广播
        Intent intentClick = new Intent();
        intentClick.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                intentClick, 0);
        remoteViews.setOnClickPendingIntent(R.id.imageView1, pendingIntent);
        manager.updateAppWidget(appWidgetId, remoteViews);
    }

    private Bitmap rotateBitmap(Context context, Bitmap srcbBitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap tmpBitmap = Bitmap.createBitmap(srcbBitmap, 0, 0,
                srcbBitmap.getWidth(), srcbBitmap.getHeight(), matrix, true);
        return tmpBitmap;
    }
}

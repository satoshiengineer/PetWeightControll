package com.example.msi.petweightcontroll;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by MSI on 2017/1/7.
 */
public class AlarmReceive extends BroadcastReceiver {
    public void onReceive(Context context,Intent intent)
    {
        final int notifyID = 1; // 通知的識別號碼
        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE); // 取得系統的通知服務
        final Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); // 通知音效的URI，在這裡使用系統內建的通知音效
        final Notification notification = new Notification.Builder(context.getApplicationContext()).setSmallIcon(R.drawable.dog).setContentTitle("Dogdiet").setContentText("餵食時間到囉!").setSound(soundUri).build();
        notificationManager.notify(notifyID, notification); // 發送通知
        Toast.makeText(context,"該餵食囉!",Toast.LENGTH_LONG).show();
    }
}

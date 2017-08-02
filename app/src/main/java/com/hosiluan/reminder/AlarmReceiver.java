package com.hosiluan.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

/**
 * Created by HoSiLuan on 7/22/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private NotificationCompat.Builder mBuilder;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("hello", "i am in receiver");
//        Intent intent1 = new Intent(context, MusicService.class);
//        context.startService(intent1);

        String name = intent.getStringExtra("name");
        Log.d("hello","name in alarm receiver " + name);
        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_home)
                .setColor(Color.BLACK)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentText(name)
                .setSound(Uri.parse("android.resource://" + context.getPackageName()+ "/" + R.raw.arpeggio))
                .setContentTitle("Reminder");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = mBuilder.build();
        notificationManager.notify(1,notification);
    }
}

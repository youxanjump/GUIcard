package com.example.user.guicard;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.os.Vibrator;
import android.widget.Toast;
import android.view.View;

import java.util.Calendar;


public class Alarm extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int notifyID = 1;
        NotificationManager noMgr = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Calendar mCal = Calendar.getInstance();
        mCal.set(Calendar.HOUR_OF_DAY,16);
        mCal.set(Calendar.MINUTE,00);
        mCal.set(Calendar.SECOND, 0);

        //當使用者按下通知欄中的通知時要開啟的 Activity
        Intent intent = new Intent(Alarm.this, MainActivity.class);
        //建立待處理意圖
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        //建立通知物件
        Notification notification = new Notification.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("GUIcard")
                .setContentText("一天又要結束了，快來找出跟你有緣分的音樂人吧！！！")
                .setContentIntent(pIntent)
                .setSmallIcon(R.drawable.face)
                .setWhen(System.currentTimeMillis())
                .build();

                long[] Vibrate = {1000, 1000};
                notification.vibrate = Vibrate;
                //執行通知
                noMgr.notify(1,notification);

    }

}

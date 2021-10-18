package com.example.lovebaby;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.lovebaby.Fragment.SleepTimerFragment;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static com.example.lovebaby.Fragment.SleepTimerFragment.CHANNEL_ID;

public class TimerService extends Service {

    private static final String TAG = "TimerService";
    private boolean isRunning = true;
    private String startTime = null;
    private String input=null;
    private int threadcnt = 0;
    private Thread thread;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: 서비스 콜백 메스드 시작");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        input = intent.getStringExtra("babyName");
        startTime = intent.getStringExtra("startTime");
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.putExtra("babyName",input);
        notificationIntent.putExtra("startTime",startTime);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.main)
                .setContentTitle("LoveBaby")
                .setContentText(input+" 수면 시간 측정 중")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        startForeground(1, builder.build());
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: 서비스 중지");
        System.out.println("ondestroy");
        isRunning = false;
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}


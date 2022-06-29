package com.example.serviceexamples;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

public class PlayerService extends Service {
    MediaPlayer player;

    public PlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();

        player = MediaPlayer.create(getApplicationContext(), R.raw.test);

        NotificationChannel channel = new NotificationChannel("channel_01", "channel_01", NotificationManager.IMPORTANCE_DEFAULT);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent,
                        PendingIntent.FLAG_IMMUTABLE);

        Notification notification =
                new Notification.Builder(this, "channel_01")
                        .setContentTitle("Music Player")
                        .setContentText("Test")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pendingIntent)
                        .build();

        // Notification ID cannot be 0.
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals("PLAY"))
            player.start();
        else if (intent.getAction().equals("PAUSE"))
            player.pause();
        else if (intent.getAction().equals("STOP"))
            player.stop();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        player.stop();
        player.release();

        super.onDestroy();
    }
}
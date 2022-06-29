package com.example.serviceexamples;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService1 extends Service {
    public MyService1() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("TAG", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("TAG", "onStartCommand: " + flags + "-" + startId);

        new Thread(new Runnable() {
            @Override
            public void run() {
                isRunning = true;

                try {
                    for (int i = 0; i < 10 && isRunning; i++) {
                        Thread.sleep(1000);
                        Log.v("TAG", "" + (i + 1));
                    }

//                    stopSelf();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                isRunning = false;
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    boolean isRunning;

    @Override
    public void onDestroy() {
        Log.v("TAG", "onDestroy");
        isRunning = false;
        super.onDestroy();
    }
}
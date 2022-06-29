package com.example.serviceexamples;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    MyBroadcastReceiver receiver;
    Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_open2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        intent1 = new Intent(MainActivity.this, MyService1.class);

        findViewById(R.id.button_start1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent1.setAction("START SERVICE");
                startService(intent1);
            }
        });

        findViewById(R.id.button_stop1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(intent1);
            }
        });

        findViewById(R.id.button_start_foo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyIntentService2.startActionFoo(MainActivity.this, "value1", "value2");
            }
        });

        findViewById(R.id.button_start_baz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyIntentService2.startActionBaz(MainActivity.this, "value3", "value4");
            }
        });


        receiver = new MyBroadcastReceiver();
        registerReceiver(receiver, new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED));
        registerReceiver(receiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
        registerReceiver(receiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
        registerReceiver(receiver, new IntentFilter("vn.edu.hust.my_action_2"));
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("vn.edu.hust.my_action"));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onDestroy();
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v("TAG", intent.getAction());
        }
    }
}
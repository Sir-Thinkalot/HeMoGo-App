package com.biomedical.hemogo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import com.biomedical.hemogo.R;

public class MqttTestActivity extends AppCompatActivity {
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mqtt_test);
        text = findViewById(R.id.mqtttesttext);
        registerReceiver(br,intentFilter);
    }

    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            text.append(intent.getStringExtra("Msg"));
        }
    };
    IntentFilter intentFilter = new IntentFilter("Data");
}
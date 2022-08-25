package com.biomedical.hemogo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import com.biomedical.hemogo.Database.Converters.JSONConverters;
import com.biomedical.hemogo.Database.JSONObjects.DatumJSON;
import com.biomedical.hemogo.R;

import java.util.ArrayList;

public class MqttLogActivity extends AppCompatActivity {
    TextView text;
    String msg;
    JSONConverters convert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_HeMoGo);
        setContentView(R.layout.activity_log);
        text = findViewById(R.id.mqtttesttext);
        registerReceiver(br,intentFilter);
    }

    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            msg = intent.getStringExtra("Msg");
            DatumJSON json = convert.jsonToDatum(msg);
            text.append(msg);
//            text.append(json.getUF().get(0).toString()+'\t');
//            text.append(json.getUF().get(1).toString()+'\t');
//            text.append(json.getPress().get(0).toString()+'\t');
//            text.append(json.getPress().get(1).toString()+'\t');
//            text.append(json.getTemp().toString()+'\t');
//            text.append(json.getCond().toString()+'\t');
//            text.append(json.getFlow().get(0).toString()+'\t');
//            text.append(json.getFlow().get(1).toString()+'\n');
        }
    };
    IntentFilter intentFilter = new IntentFilter("Data");
}
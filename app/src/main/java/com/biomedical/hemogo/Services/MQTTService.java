package com.biomedical.hemogo.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MQTTService extends Service {
    public MQTTService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
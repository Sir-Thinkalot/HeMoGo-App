package com.biomedical.hemogo.Services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import com.biomedical.hemogo.Database.Converters.JSONConverters;
import com.biomedical.hemogo.Database.Entities.Alerts;
import com.biomedical.hemogo.Database.Entities.Data;
import com.biomedical.hemogo.Database.Entities.Patient;
import com.biomedical.hemogo.Database.Entities.User;
import com.biomedical.hemogo.Database.JSONObjects.DatumJSON;
import com.biomedical.hemogo.Database.RoomDB;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;

public class MQTTService extends Service {
    RoomDB database;
    MqttAndroidClient client;
    IMqttToken conToken, disToken, unsToken, subToken, pubToken;
    Patient patient;
    List<Patient> patients = new ArrayList<>();
    DatumJSON datum;
    Data data;
    String macnum, topics;
    Alerts alerts;
    JSONConverters convert;
    User user;

    public MQTTService() {
    }

    public class binder extends Binder {
        MQTTService getService() {
//             Return this instance of LocalService so clients can call public methods
            return MQTTService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected class mReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("SetSub")){
                patient = (Patient) intent.getSerializableExtra("Patient");
                if (intent.getBooleanExtra("Sub",false)) {
                    start(patient);
                }
                else {
                    stop(patient);
                }
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        database = RoomDB.getInstance(MQTTService.this);

        mReceiver receiver = new mReceiver();
        IntentFilter intentFilter = new IntentFilter("SetSub");
        this.registerReceiver(receiver,intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        user = (User) intent.getSerializableExtra("User");
        String clientID = MqttClient.generateClientId();
        String url = user.getBrokerURL() + ":" + user.getPort();
        client = new MqttAndroidClient(getApplicationContext(),url,clientID);
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Intent intent = new Intent();
                if (topic.matches("(.*)/DATA")){
                    intent.setAction("Data");
//                    Log.d("Data",new String(message.getPayload()));
//                    topics = topic;
//                    data = database.mainDAO().getData(topics.replace("/DATA",""));
//                    Log.d("Macnum",data.getMachineNumber());
//                    DatumJSON json = convert.jsonToDatum(new String(message.getPayload()));
//                    data.appendDatum(data.getArterialPressure()     ,json.getPress().get(0));
//                    data.appendDatum(data.getVenousPressure()       ,json.getPress().get(1));
//                    data.appendDatum(data.getDialysatePressure()    ,json.getPress().get(2));
//
//                    data.appendDatum(data.getUfRate()               ,json.getUF().get(0));
//                    data.appendDatum(data.getUfRemove()             ,json.getUF().get(1));
//
//                    data.appendDatum(data.getDialysateTemperature() ,json.getTemp());
//                    data.appendDatum(data.getDialysateConductivity(),json.getCond());
//                    data.appendDatum(data.getDialysateFlowRate()    ,json.getFlow().get(0));
//                    data.appendDatum(data.getHeparinFlowRate()      ,json.getFlow().get(1));
//                    data.appendDatum(data.getBloodFlowRate()        ,json.getFlow().get(2));
//                    database.mainDAO().update(
//                            data.getMachineNumber(),
//                            new ArrayList<>(),
//                            data.getUfRate(),
//                            data.getUfRemove(),
//                            data.getArterialPressure(),
//                            data.getVenousPressure(),
//                            data.getDialysatePressure(),
//                            data.getDialysateConductivity(),
//                            data.getDialysateFlowRate(),
//                            data.getDialysateTemperature(),
//                            data.getBloodFlowRate(),
//                            data.getHeparinFlowRate()
//                    );
                }
                else if (topic.matches("(.*)/ALERT")){
                    intent.setAction("Alert");
                }
                else if (topic.matches("(.*)/START")){
                    intent.setAction("Start");
                }
                String msg = new String(message.getPayload());
                Log.d("Message",topic+": "+msg);
                intent.putExtra("Topic",topic);
                intent.putExtra("Msg",msg);
                intent.putExtra("Data",data);
                sendBroadcast(intent);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
        connect(client);
        return super.onStartCommand(intent, flags, startId);
    }

    public void update(){
        Intent intent = new Intent();
        intent.setAction("Update");
        sendBroadcast(intent);
    }

    public void connect(MqttAndroidClient client){
        try{
            conToken = client.connect();
            conToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(getApplicationContext(),"Connection success!!",Toast.LENGTH_SHORT).show();
//                    TODO : Subscribe to all previously subscribed topics
                    patients = database.mainDAO().getPatients(user.getPatientIDs());
                    for (int i = 0; i < patients.size(); i++) {
                        if (patients.get(i).getConnection_status()){
                            start(patients.get(i));
                        }
                        Log.d("Macnum",patients.get(i).getMachineNumber());
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(getApplicationContext(),"Connection failed, " + exception.getMessage().toString(),Toast.LENGTH_LONG).show();
                    Log.d("Connection Failed", exception.getMessage());
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    public void disconnect (MqttAndroidClient client){
        try{
            disToken = client.disconnect();
            disToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(getApplicationContext(),"Disconnected!!",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(getApplicationContext(),"Can't Disconnect",Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void pub(String topic, String message){
        try {
            pubToken = client.publish(topic, message.getBytes(),2,false);
        } catch ( MqttException e) {
            e.printStackTrace();
        }
    }

    public void sub(MqttAndroidClient client, String topic, int qos){
        try{
            subToken = client.subscribe(topic,qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d("Subscribed",topic+" qos = "+qos);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.d("Sub Failed",topic);
                }
            });
        }catch (MqttException e){
            e.printStackTrace();
        }
    }

    public void unsub(MqttAndroidClient client, String topic){
        try {
            unsToken = client.unsubscribe(topic);
            unsToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d("Unsubscribed",topic);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.d("Unsub Failed",topic);
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void start(Patient patient){
        try{
            String macnum = patient.getMachineNumber();
            sub(client,macnum+"/DATA",0);
            sub(client,macnum+"/ALERT",2);
            sub(client,macnum+"/START",2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.mainDAO().setConn(patient.getID(),true);
            update();
        }
    }

    public void stop(Patient patient){
        try{
            String macnum = patient.getMachineNumber();
            unsub(client,macnum+"/DATA");
            unsub(client,macnum+"/ALERT");
            unsub(client,macnum+"/START");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.mainDAO().setConn(patient.getID(),false);
            update();
        }
    }


}
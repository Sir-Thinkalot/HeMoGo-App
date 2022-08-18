package com.biomedical.hemogo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.biomedical.hemogo.Database.Entities.Alerts;
import com.biomedical.hemogo.Database.Entities.Data;
import com.biomedical.hemogo.Database.Entities.Patient;
import com.biomedical.hemogo.Database.RoomDB;
import com.biomedical.hemogo.R;

import java.util.List;

public class PatientDetailActivity extends AppCompatActivity {
    SeekBar aPress_bar, vPress_bar, dPress_bar;
    TextView patient_name, machine_number,
            aPress_val, vPress_val, dPress_val,
            ufGoal_val, ufRemove_val, ufTime_val, ufRate_val,
            temp_val, cond_val, dFlow_val, hFlow_val, bFlow_val;
    ImageView bloodAlert, bubbleAlert;

    Patient patient;
    Data data;
    RoomDB database;
    List<Alerts> alerts;

    View.OnTouchListener disableTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);

        patient_name = findViewById(R.id.detail_patient_name);
        machine_number = findViewById(R.id.detail_machine_num);

        ufGoal_val = findViewById(R.id.val_UF_Goal);
        ufRemove_val = findViewById(R.id.val_UF_Remove);
        ufTime_val = findViewById(R.id.val_UF_Time);
        ufRate_val = findViewById(R.id.val_UF_Rate);

        temp_val = findViewById(R.id.temp_val);
        cond_val = findViewById(R.id.cond_val);
        dFlow_val = findViewById(R.id.Dflow_val);
        hFlow_val = findViewById(R.id.HFlow_val);
        bFlow_val = findViewById(R.id.Bflow_val);

        aPress_val = findViewById(R.id.APressVal);
        vPress_val = findViewById(R.id.VPressVal);
        dPress_val = findViewById(R.id.DPressVal);

        aPress_bar = findViewById(R.id.APressBar);
        vPress_bar = findViewById(R.id.VPressBar);
        dPress_bar = findViewById(R.id.DPressBar);

        aPress_bar.setOnTouchListener(disableTouch);
        vPress_bar.setOnTouchListener(disableTouch);
        dPress_bar.setOnTouchListener(disableTouch);

        bloodAlert = findViewById(R.id.blood_indicator);
        bubbleAlert = findViewById(R.id.bubble_indicator);

        database = RoomDB.getInstance(PatientDetailActivity.this);
        patient = (Patient) getIntent().getSerializableExtra("Patient");
        data = database.mainDAO().getData(patient.getMachineNumber());
        alerts = database.mainDAO().getAlerts(patient.getMachineNumber());

        patient_name.setText(patient.getPatientName());
        machine_number.setText(patient.getMachineNumber());

        aPress_bar.setProgress(Math.round(data.getLastDatum(data.getArterialPressure())*10));
        vPress_bar.setProgress(Math.round(data.getLastDatum(data.getVenousPressure())*10));
        dPress_bar.setProgress(Math.round(data.getLastDatum(data.getDialysatePressure())*10));

        aPress_val.setText(String.valueOf(data.getLastDatum(data.getArterialPressure())));
        vPress_val.setText(String.valueOf(data.getLastDatum(data.getVenousPressure())));
        dPress_val.setText(String.valueOf(data.getLastDatum(data.getDialysatePressure())));

        temp_val.setText(String.valueOf(data.getLastDatum(data.getDialysateTemperature())));
        cond_val.setText(String.valueOf(data.getLastDatum(data.getDialysateConductivity())));
        dFlow_val.setText(String.valueOf(data.getLastDatum(data.getDialysateFlowRate())));
        hFlow_val.setText(String.valueOf(data.getLastDatum(data.getHeparinFlowRate())));
        bFlow_val.setText(String.valueOf(data.getLastDatum(data.getBloodFlowRate())));

        if (database.mainDAO().getLastAlert("BLD")){
            bloodAlert.setImageResource(R.drawable.ic_warning_active);
        }
        else{
            bloodAlert.setImageResource(R.drawable.ic_warning_idle);
        }

        if (database.mainDAO().getLastAlert("BUB")){
            bubbleAlert.setImageResource(R.drawable.ic_warning_active);
        }
        else{
            bubbleAlert.setImageResource(R.drawable.ic_warning_idle);
        }

    }
}
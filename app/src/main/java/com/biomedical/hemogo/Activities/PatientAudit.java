package com.biomedical.hemogo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.biomedical.hemogo.Database.Entities.Data;
import com.biomedical.hemogo.Database.Entities.Patient;
import com.biomedical.hemogo.Database.Entities.User;
import com.biomedical.hemogo.Database.RoomDB;
import com.biomedical.hemogo.R;

import java.util.ArrayList;
import java.util.List;

public class PatientAudit extends AppCompatActivity {
    Patient patient;
    RoomDB database;
    ArrayList<Integer> patientIDs;
    boolean flag, flag2;
    User user;
//TODO: IF PATIENT EXIST THEN GET PATIENT INSTEAD FROM DB
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_audit);
        database = RoomDB.getInstance(this);
        TextView title = findViewById(R.id.audit_title);
        EditText name = findViewById(R.id.edit_patient_name);
        EditText macnum = findViewById(R.id.edit_machine_number);
        if(getIntent().getIntExtra("requestcode", 100) == 101){
            title.setText("Add Patient");
            user = (User) getIntent().getSerializableExtra("user");
            patientIDs = new ArrayList<>();
            patientIDs.addAll(user.getPatientIDs());
            flag = false;
        }
        else if (getIntent().getIntExtra("requestcode",100)==102){
             patient = (Patient) getIntent().getSerializableExtra("editpatient");
             title.setText("Edit Patient Data");
             name.setText(patient.getPatientName());
             macnum.setText(patient.getMachineNumber());
             flag = true;
        }

        TextView addButton = findViewById(R.id.finish_audit);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (flag){
                        database.mainDAO().update(patient.getID(),name.getText().toString(),macnum.getText().toString());
                    }
                    else{
                        List<Patient> patients = database.mainDAO().getAllPatients();
                        patient = new Patient();
                        patient.setPatientName(name.getText().toString());
                        patient.setMachineNumber(macnum.getText().toString());
                        flag2 = true;
                        for (int i = 0; i < patients.size(); i++){
                            if(patients.get(i).getPatientName().equals(patient.getPatientName()) &&
                            patients.get(i).getMachineNumber().equals(patient.getMachineNumber())){
                                patient = database.mainDAO().getPatient(name.getText().toString(),macnum.getText().toString());
                                patient.setConnection_status(false);
                                database.mainDAO().setConn(patient.getID(),false);
                                flag2 = false;
                                break;
                            }
                        }
                        if (flag2){
                            database.mainDAO().insert(patient);
                            Data data = new Data();
                            data.setMachineNumber(macnum.getText().toString());
                            data.setArterialPressure(new ArrayList<>());
                            data.setBloodFlowRate(new ArrayList<>());
                            data.setDialysateConductivity(new ArrayList<>());
                            data.setDialysateFlowRate(new ArrayList<>());
                            data.setDialysatePressure(new ArrayList<>());
                            data.setDialysateTemperature(new ArrayList<>());
                            data.setHeparinFlowRate(new ArrayList<>());
                            data.setUfRate(new ArrayList<>());
                            data.setUfRemove(new ArrayList<>());
                            data.setVenousPressure(new ArrayList<>());
                            database.mainDAO().insert(data);
                        }
                        int patientID = database.mainDAO().getPatientID(patient.getPatientName(),patient.getMachineNumber());
                        if(!patientIDs.contains(patientID)){
                            patientIDs.add(patientID);
                        }
                        database.mainDAO().updatePatientList(user.getID(),patientIDs);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Please Fill All Spaces", Toast.LENGTH_SHORT);
                    return;
                }

                Intent intent = new Intent();
                setResult(101, intent);
                finish();
            }
        });
    }
}
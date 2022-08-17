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

public class PatientAudit extends AppCompatActivity {
    Patient patient;
    RoomDB database;
    ArrayList<Integer> patientIDs;
    boolean flag;
    User user;

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
                        patient = new Patient();
                        patient.setPatientName(name.getText().toString());
                        patient.setMachineNumber(macnum.getText().toString());
                        database.mainDAO().insert(patient);
                        Data data = new Data();
                        data.setMachineNumber(macnum.getText().toString());
                        database.mainDAO().insert(data);
                        patientIDs.add(database.mainDAO().getPatientID(patient.getPatientName(),patient.getMachineNumber()));
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
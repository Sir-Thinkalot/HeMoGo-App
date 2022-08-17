package com.biomedical.hemogo.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.biomedical.hemogo.Adapters.PatientListAdapter;
import com.biomedical.hemogo.Database.Entities.Patient;
import com.biomedical.hemogo.Database.Entities.User;
import com.biomedical.hemogo.Database.RoomDB;
import com.biomedical.hemogo.Interfaces.PatientCardClickListener;
import com.biomedical.hemogo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    RecyclerView recyclerView;
    FloatingActionButton fab_patient;
    RoomDB database;
    List<Patient> patients = new ArrayList<>();
    List<Integer> patientIDs = new ArrayList<>();
    PatientListAdapter patientListAdapter;
    Patient selectedPatient;
    User user;
    int ID;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
//                    Intent data = result.getData();
                    switch (result.getResultCode()){
                        case 101:
                            user = database.mainDAO().getUser(user.getUsername(),user.getPassword());
                            patients = database.mainDAO().getPatients(user.getPatientIDs());
                            updateRecycler(patients);

                        case 102:
                    }
                }
            }
    );

    protected class mReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("Update")){
                patients = database.mainDAO().getPatients(user.getPatientIDs());
                updateRecycler(patients);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_HeMoGo);
        setContentView(R.layout.activity_main);

        user = (User) getIntent().getSerializableExtra("User");
        ID = user.getID();
        patientIDs = user.getPatientIDs();

        TextView username = findViewById(R.id.profile_name);
        username.setText(user.getUsername());

        recyclerView = findViewById(R.id.recycler_hub);
        fab_patient = findViewById(R.id.fab_patient);

        fab_patient.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, PatientAudit.class);
            intent.putExtra("requestcode",101);
            intent.putExtra("user",user);
            activityResultLauncher.launch(intent);
        });

        database = RoomDB.getInstance(this);
        patients = database.mainDAO().getPatients(patientIDs);
        updateRecycler(patients);
        mReceiver receiver = new mReceiver();
        IntentFilter intentFilter = new IntentFilter("Update");
        this.registerReceiver(receiver,intentFilter);
    }

    private void updateRecycler(List<Patient> patients) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));

        patientListAdapter = new PatientListAdapter(MainActivity.this,patients,patientCardClickListener);
        recyclerView.setAdapter(patientListAdapter);
    }

    private final PatientCardClickListener patientCardClickListener = new PatientCardClickListener() {
        @Override
        public void onClick(Patient patient) {
            Intent intent = new Intent(MainActivity.this,MqttTestActivity.class);
            intent.putExtra("Patient",patient);
            startActivity(intent);
        }

        @Override
        public void onLongClick(Patient patient, CardView cardView) {
            selectedPatient = new Patient();
            selectedPatient = patient;
            showPopup(cardView, selectedPatient);
        }
    };

    private void showPopup(CardView cardView, Patient selectedPatient) {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this,cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.patient_card_popup);
        if(selectedPatient.getConnection_status()){
            popupMenu.getMenu().findItem(R.id.patient_conn).setTitle("Disconnect");
        }
        else{
            popupMenu.getMenu().findItem(R.id.patient_conn).setTitle("Connect");
        }
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.patient_conn:
                if (selectedPatient.getConnection_status()){
                    Intent sub = new Intent();
                    sub.setAction("SetSub");
                    sub.putExtra("Sub",false);
                    sub.putExtra("Patient", selectedPatient);
                    getApplicationContext().sendBroadcast(sub);
                    return true;
                }
                else{
                    Intent sub = new Intent();
                    sub.setAction("SetSub");
                    sub.putExtra("Sub",true);
                    sub.putExtra("Patient", selectedPatient);
                    getApplicationContext().sendBroadcast(sub);
                    return true;
                }

            case R.id.patient_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder .setMessage(R.string.confirm_dialog_message)
                        .setTitle(R.string.confirm_dialog_title)
                        .setNegativeButton("Yes", (dialogInterface, i) -> {
                            database.mainDAO().delete(selectedPatient);
                            patients = database.mainDAO().getPatients(patientIDs);
                            updateRecycler(patients);
                        })
                        .setPositiveButton("No", (dialogInterface, i) -> {

                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;

            case R.id.patient_edit:
                Intent intent = new Intent(MainActivity.this,PatientAudit.class);
                intent.putExtra("editpatient",selectedPatient);
                intent.putExtra("requestcode",102);
                activityResultLauncher.launch(intent);
                return true;
        }
        return false;
    }
}
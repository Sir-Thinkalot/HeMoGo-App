package com.biomedical.hemogo.Database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.biomedical.hemogo.Database.Entities.Alerts;
import com.biomedical.hemogo.Database.Entities.Data;
import com.biomedical.hemogo.Database.Entities.Patient;
import com.biomedical.hemogo.Database.Entities.User;

import java.util.ArrayList;
import java.util.List;

import static androidx.room.OnConflictStrategy.ABORT;
import static androidx.room.OnConflictStrategy.FAIL;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDAO {
    @Insert(onConflict = REPLACE)
    void insert(Patient patient);

    @Insert(onConflict = REPLACE)
    void insert(Alerts alerts);

    @Insert(onConflict = REPLACE)
    void insert(Data data);

    @Insert(onConflict = REPLACE)
    void insert(User user);

    @Query("SELECT * FROM patient WHERE ID IN (:UserID) ORDER BY id ASC")
    List<Patient> getPatients(List<Integer> UserID);

    @Query("SELECT uname FROM user ORDER BY ID ASC")
    List<String> getAllUsername();

    @Query("SELECT mail FROM user ORDER BY ID ASC")
    List<String> getAllMails();

    @Query("SELECT * FROM patient ORDER BY id ASC")
    List<Patient> getAllPatients();

    @Query("SELECT ID FROM patient WHERE name = :na AND machine = :mn")
    int getPatientID(String na, String mn);

    @Query("SELECT * FROM patient WHERE name = :na AND machine = :mn")
    Patient getPatient(String na,String mn);

    @Query("UPDATE patient SET name = :name, machine =:macnum WHERE ID = :id")
    void update(int id, String name, String macnum);

    @Delete
    void delete(Patient patient);

    @Query("UPDATE patient SET status = :conn WHERE ID = :id")
    void setConn(int id, boolean conn);

    @Query("SELECT * FROM data WHERE machinenumber = :mn LIMIT 1")
    Data getData(String mn);

    @Query("UPDATE data SET timestamp = :ts, ufrate = :ura, ufremove = :ure, apress = :apr, vpress = :vpr, dpress = :dpr, dcond = :dcd, dflow = :dfr, dtemp = :dtm, bflow = :bfr, hflow = :hfr WHERE machinenumber = :mn")
    void update(String mn,
                ArrayList<String> ts,
                ArrayList<Float> ura,
                ArrayList<Float> ure,
                ArrayList<Float> apr,
                ArrayList<Float> vpr,
                ArrayList<Float> dpr,
                ArrayList<Float> dcd,
                ArrayList<Float> dfr,
                ArrayList<Float> dtm,
                ArrayList<Float> bfr,
                ArrayList<Float> hfr);

    @Query("SELECT * FROM alerts WHERE macnum = :mn")
    List<Alerts> getAlerts(String mn);

    @Query("SELECT * FROM alerts WHERE macnum = :mn AND code = :code")
    List<Alerts> getAlertsWithCode(String mn, String code);

    @Query("SELECT alert FROM ALERTS WHERE code = :code ORDER BY ID DESC LIMIT 1 ")
    boolean getLastAlert(String code);

    @Query("SELECT * FROM user WHERE uname = :uname AND pass = :pass LIMIT 1")
    User getUser(String uname, String pass);

    @Query("SELECT * FROM user WHERE uname = :uname")
    int isExist(String uname);

    @Query("SELECT pass FROM user WHERE uname = :uname")
    String pass(String uname);

    @Query("UPDATE user SET patientlist = :PatientID WHERE ID = :id")
    void updatePatientList(int id, ArrayList<Integer> PatientID);
}

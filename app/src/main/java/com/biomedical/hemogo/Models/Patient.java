package com.biomedical.hemogo.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "patient")
public class Patient implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int ID = 0;

    @ColumnInfo(name = "name")
    String patientName = "";

    @ColumnInfo(name = "machine")
    String machineNumber = "";

    @ColumnInfo(name = "status")
    Boolean connection_status = false;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(String machineNumber) {
        this.machineNumber = machineNumber;
    }

    public Boolean getConnection_status() {
        return connection_status;
    }

    public void setConnection_status(Boolean connection_status) {
        this.connection_status = connection_status;
    }
}

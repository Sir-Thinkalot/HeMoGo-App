package com.biomedical.hemogo.Database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.biomedical.hemogo.Database.Converters.IntTypeConverters;

import java.io.Serializable;
import java.util.ArrayList;

@Entity (tableName = "user", indices = {@Index(value =  {"uname"}, unique = true)})
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int ID = 0;

    @ColumnInfo(name = "uname")
    String username = "";

    @ColumnInfo(name = "pass")
    String password = "";

    @ColumnInfo(name = "mail")
    public String eMail = "";

    @ColumnInfo(name = "url")
    String brokerURL = "";

    @ColumnInfo(name = "port")
    int port = 1883;

    @ColumnInfo(name = "patientlist")
    @TypeConverters(IntTypeConverters.class)
    ArrayList<Integer> patientIDs  = new ArrayList<Integer>();

    @ColumnInfo (name = "status")
    public boolean Connection_status = false;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getBrokerURL() {
        return brokerURL;
    }

    public void setBrokerURL(String brokerURL) {
        this.brokerURL = brokerURL;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean getStatus() {
        return Connection_status;
    }

    public void setStatus(boolean connection_status) {
        Connection_status = connection_status;
    }

    public ArrayList<Integer> getPatientIDs() {
        return patientIDs;
    }

    public void setPatientIDs(ArrayList<Integer> patientIDs) {
        this.patientIDs = patientIDs;
    }
}

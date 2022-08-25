package com.biomedical.hemogo.Database.Entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.biomedical.hemogo.Database.Converters.FloatTypeConverters;
import com.biomedical.hemogo.Database.Converters.StringTypeConverters;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "data")
public class Data implements Serializable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "machinenumber")
    String machineNumber = "";

    @ColumnInfo(name = "timestamp")
    @Nullable
    @TypeConverters(StringTypeConverters.class)
    ArrayList<String> timestamp;

    @ColumnInfo(name = "ufrate")
    @Nullable
    @TypeConverters(FloatTypeConverters.class)
    ArrayList<Float> ufRate;

    @ColumnInfo(name = "ufremove")
    @Nullable
    @TypeConverters(FloatTypeConverters.class)
    ArrayList<Float> ufRemove = new ArrayList<Float>();

    @ColumnInfo(name = "apress")
    @Nullable
    @TypeConverters(FloatTypeConverters.class)
    ArrayList<Float> arterialPressure = new ArrayList<Float>();

    @ColumnInfo(name = "vpress")
    @Nullable
    @TypeConverters(FloatTypeConverters.class)
    ArrayList<Float> venousPressure = new ArrayList<Float>();

    @ColumnInfo(name = "dpress")
    @Nullable
    @TypeConverters(FloatTypeConverters.class)
    ArrayList<Float> dialysatePressure = new ArrayList<Float>();

    @ColumnInfo(name = "dtemp")
    @Nullable
    @TypeConverters(FloatTypeConverters.class)
    ArrayList<Float> dialysateTemperature = new ArrayList<Float>();

    @ColumnInfo(name = "dcond")
    @Nullable
    @TypeConverters(FloatTypeConverters.class)
    ArrayList<Float> dialysateConductivity = new ArrayList<Float>();

    @ColumnInfo(name = "dflow")
    @Nullable
    @TypeConverters(FloatTypeConverters.class)
    ArrayList<Float> dialysateFlowRate = new ArrayList<Float>();

    @ColumnInfo(name = "hflow")
    @Nullable
    @TypeConverters(FloatTypeConverters.class)
    ArrayList<Float> heparinFlowRate = new ArrayList<Float>();

    @ColumnInfo(name = "bflow")
    @Nullable
    @TypeConverters(FloatTypeConverters.class)
    ArrayList<Float> bloodFlowRate = new ArrayList<Float>();

    public String getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(String machineNumber) {
        this.machineNumber = machineNumber;
    }

    public ArrayList<String> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ArrayList<String> timestamp) {
        this.timestamp = timestamp;
    }

    public String getLastTimestamp() {
        return timestamp.get(timestamp.size()-1);
    }

    public void appendTimestamp(ArrayList<String> timestamps, String time){
        timestamp.add(time);
        timestamp = timestamps;
    }

    public ArrayList<Float> getUfRate() {
        return ufRate;
    }

    public void setUfRate(ArrayList<Float> ufRate) {
        this.ufRate = ufRate;
    }

    public ArrayList<Float> getUfRemove() {
        return ufRemove;
    }

    public void setUfRemove(ArrayList<Float> ufRemove) {
        this.ufRemove = ufRemove;
    }

    public ArrayList<Float> getArterialPressure() {
        return arterialPressure;
    }

    public void setArterialPressure(ArrayList<Float> arterialPressure) {
        this.arterialPressure = arterialPressure;
    }

    public ArrayList<Float> getVenousPressure() {
        return venousPressure;
    }

    public void setVenousPressure(ArrayList<Float> venousPressure) {
        this.venousPressure = venousPressure;
    }

    public ArrayList<Float> getDialysatePressure() {
        return dialysatePressure;
    }

    public void setDialysatePressure(ArrayList<Float> dialysatePressure) {
        this.dialysatePressure = dialysatePressure;
    }

    public ArrayList<Float> getDialysateTemperature() {
        return dialysateTemperature;
    }

    public void setDialysateTemperature(ArrayList<Float> dialysateTemperature) {
        this.dialysateTemperature = dialysateTemperature;
    }

    public ArrayList<Float> getDialysateConductivity() {
        return dialysateConductivity;
    }

    public void setDialysateConductivity(ArrayList<Float> dialysateConductivity) {
        this.dialysateConductivity = dialysateConductivity;
    }

    public ArrayList<Float> getDialysateFlowRate() {
        return dialysateFlowRate;
    }

    public void setDialysateFlowRate(ArrayList<Float> dialysateFlowRate) {
        this.dialysateFlowRate = dialysateFlowRate;
    }

    public ArrayList<Float> getHeparinFlowRate() {
        return heparinFlowRate;
    }

    public void setHeparinFlowRate(ArrayList<Float> heparinFlowRate) {
        this.heparinFlowRate = heparinFlowRate;
    }

    public ArrayList<Float> getBloodFlowRate() {
        return bloodFlowRate;
    }

    public void setBloodFlowRate(ArrayList<Float> bloodFlowRate) {
        this.bloodFlowRate = bloodFlowRate;
    }

    public float getLastDatum(ArrayList<Float> data){
        if(data.size()<1){
            return 0;
        }
        else{
            return data.get(data.size()-1);
        }
    }

    public void appendDatum(ArrayList<Float> data, Float datum){
        data.add(datum);
    }
}


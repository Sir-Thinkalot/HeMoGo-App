package com.biomedical.hemogo.Database.JSONObjects;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class DatumJSON {

    ArrayList<Float> UF = new ArrayList<>();
    ArrayList<Float> Press = new ArrayList<>();
    Float Temp = Float.valueOf(0);
    Float Cond = Float.valueOf(0);
    ArrayList<Float> Flow = new ArrayList<>();

    public ArrayList<Float> getUF() {
        return UF;
    }

    public void setUF(ArrayList<Float> UF) {
        this.UF = UF;
    }

    public ArrayList<Float> getPress() {
        return Press;
    }

    public void setPress(ArrayList<Float> press) {
        Press = press;
    }

    public Float getTemp() {
        return Temp;
    }

    public void setTemp(Float temp) {
        Temp = temp;
    }

    public Float getCond() {
        return Cond;
    }

    public void setCond(Float cond) {
        Cond = cond;
    }

    public ArrayList<Float> getFlow() {
        return Flow;
    }

    public void setFlow(ArrayList<Float> flow) {
        Flow = flow;
    }
}



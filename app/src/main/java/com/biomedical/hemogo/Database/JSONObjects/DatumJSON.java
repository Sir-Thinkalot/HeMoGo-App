package com.biomedical.hemogo.Database.JSONObjects;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class DatumJSON {

    ArrayList<Float> UF = new ArrayList<>();
    ArrayList<Float> PRESS = new ArrayList<>();
    Float TEMP = Float.valueOf(0);
    Float EC = Float.valueOf(0);
    Float FLOW = Float.valueOf(0);

    public ArrayList<Float> getUF() {
        return UF;
    }

    public void setUF(ArrayList<Float> UF) {
        this.UF = UF;
    }

    public ArrayList<Float> getPRESS() {
        return PRESS;
    }

    public void setPRESS(ArrayList<Float> PRESS) {
        this.PRESS = PRESS;
    }

    public Float getTEMP() {
        return TEMP;
    }

    public void setTEMP(Float TEMP) {
        this.TEMP = TEMP;
    }

    public Float getEC() {
        return EC;
    }

    public void setEC(Float EC) {
        this.EC = EC;
    }

    public Float getFLOW() {
        return FLOW;
    }

    public void setFLOW(Float FLOW) {
        this.FLOW = FLOW;
    }
}



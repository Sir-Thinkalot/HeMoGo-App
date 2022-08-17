package com.biomedical.hemogo.Database.Converters;

import com.biomedical.hemogo.Database.JSONObjects.AlertJSON;
import com.biomedical.hemogo.Database.JSONObjects.DatumJSON;
import com.google.gson.Gson;

public class JSONConverters {
    private static final Gson gson = new Gson();

    public static DatumJSON jsonToDatum(String msg){
        return gson.fromJson(msg,DatumJSON.class);
    }

    public static String datumToJson(DatumJSON datum){
        return gson.toJson(datum);
    }

    public static AlertJSON jsontoAlert(String msg){
        return gson.fromJson(msg,AlertJSON.class);
    }
}

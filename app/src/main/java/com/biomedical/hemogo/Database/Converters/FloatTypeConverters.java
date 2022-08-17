package com.biomedical.hemogo.Database.Converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FloatTypeConverters {
    private static Gson gson = new Gson();

    @TypeConverter
    public static ArrayList<Float> stringToIntList(String data) {
        if (data == null) {
            return new ArrayList<Float>();
        } else {
            Type listType = new TypeToken<ArrayList<Integer>>(){}.getType();
            return gson.fromJson(data, listType);
        }
    }

    @TypeConverter
    public static String intListToString(ArrayList<Float> data) {
        if (data.isEmpty()) {
            return null;
        } else {
            return gson.toJson(data);
        }
    }
}

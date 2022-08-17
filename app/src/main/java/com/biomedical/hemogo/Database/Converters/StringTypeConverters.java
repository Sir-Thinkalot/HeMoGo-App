package com.biomedical.hemogo.Database.Converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StringTypeConverters {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static ArrayList<String> stringToStringList(String data) {
        if (data == null) {
            return new ArrayList<>();
        }

        Type listType = new TypeToken<ArrayList<String>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String stringListToString(ArrayList<String> data) {
        return gson.toJson(data);
    }
}

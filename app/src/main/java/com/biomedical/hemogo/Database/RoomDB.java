package com.biomedical.hemogo.Database;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.biomedical.hemogo.Database.Converters.FloatTypeConverters;
import com.biomedical.hemogo.Database.Converters.IntTypeConverters;
import com.biomedical.hemogo.Database.Converters.StringTypeConverters;
import com.biomedical.hemogo.Database.Entities.Alerts;
import com.biomedical.hemogo.Database.Entities.Data;
import com.biomedical.hemogo.Database.Entities.Patient;
import com.biomedical.hemogo.Database.Entities.User;

@Database(entities = {
        User.class,
        Patient.class,
        Data.class,
        Alerts.class},
        version = 7, exportSchema = false)

@TypeConverters({
        IntTypeConverters.class,
        FloatTypeConverters.class,
        StringTypeConverters.class})

public abstract class RoomDB extends RoomDatabase {
    private static RoomDB database;
    private static String DATABASE_NAME = "Hemodialisis";

    public synchronized static RoomDB getInstance(Context context){
        if (database == null){
            database = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public synchronized static RoomDB getInstance(Fragment fragment){
        if (database == null){
            database = Room.databaseBuilder(fragment.getActivity().getApplicationContext(),
                    RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract MainDAO mainDAO();
}

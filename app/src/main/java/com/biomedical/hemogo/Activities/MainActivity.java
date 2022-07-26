package com.biomedical.hemogo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.biomedical.hemogo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_HeMoGo);
        setContentView(R.layout.activity_main);
    }
}
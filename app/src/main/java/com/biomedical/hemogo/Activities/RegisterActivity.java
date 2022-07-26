package com.biomedical.hemogo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.biomedical.hemogo.R;

public class RegisterActivity extends AppCompatActivity {

    TextView buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_HeMoGo);
        setContentView(R.layout.activity_register);

        buttonRegister = findViewById(R.id.button_register_new);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
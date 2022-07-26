package com.biomedical.hemogo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.biomedical.hemogo.R;

public class LoginActivity extends AppCompatActivity {

    TextView buttonLogin, buttonRegister;
    EditText pas,usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_HeMoGo);
        setContentView(R.layout.activity_login);

        usr = findViewById(R.id.editTextTextEmailAddress);
        pas = findViewById(R.id.editTextTextPassword);


        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (credentials(usr.getText().toString(),pas.getText().toString())){
                    openMainActivity();
                    finish();
                }
                else Toast.makeText(LoginActivity.this, "Username or Password is Wrong", Toast.LENGTH_LONG);
            }
        });

        buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


    }

    public boolean credentials(String u, String p){
        if (u.equals("admin") && p.equals("admin")) {
            return true;
        }
        else return false;
    }

    public void openMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
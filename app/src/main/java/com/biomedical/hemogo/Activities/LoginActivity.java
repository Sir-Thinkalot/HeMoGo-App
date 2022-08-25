package com.biomedical.hemogo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.biomedical.hemogo.Database.Entities.User;
import com.biomedical.hemogo.Database.RoomDB;
import com.biomedical.hemogo.R;
import com.biomedical.hemogo.Services.MQTTService;

public class LoginActivity extends AppCompatActivity {

    TextView buttonLogin, buttonRegister;
    EditText pas,usr;
    User user;
    RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_HeMoGo);
        setContentView(R.layout.activity_login);


         database = RoomDB.getInstance(LoginActivity.this);

        usr = findViewById(R.id.editTextTextEmailAddress);
        pas = findViewById(R.id.editTextTextPassword);


        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(view -> {
            if (credentials(usr.getText().toString(),pas.getText().toString())){
                user = database.mainDAO().getUser(usr.getText().toString(),pas.getText().toString());
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("User",user);
                startActivity(intent);
                Intent intent2 = new Intent(getApplicationContext(),MQTTService.class);
                intent2.putExtra("User",user);
                startService(intent2);
                finish();
            }
            else Toast.makeText(LoginActivity.this, "Username or Password is Wrong", Toast.LENGTH_LONG).show();
        });

        buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
            finish();
        });


    }

    public boolean credentials(String u, String p){
        if (0 < database.mainDAO().isExist(u)) {
            return p.equals(database.mainDAO().pass(u));
        }
        return false;
    }
}
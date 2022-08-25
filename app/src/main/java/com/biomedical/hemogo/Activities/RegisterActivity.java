package com.biomedical.hemogo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.biomedical.hemogo.Database.Entities.User;
import com.biomedical.hemogo.Database.RoomDB;
import com.biomedical.hemogo.R;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    TextView buttonRegister;
    TextInputEditText uname, mail, pass, cpass, url, port;
    RoomDB database;
    User user;
    List<String> usernames, mails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_HeMoGo);
        setContentView(R.layout.activity_register);

        database = RoomDB.getInstance(RegisterActivity.this);
        usernames = database.mainDAO().getAllUsername();
        mails = database.mainDAO().getAllMails();
        uname = findViewById(R.id.edit_uname);
        mail = findViewById(R.id.edit_email);
        pass = findViewById(R.id.edit_password);
        cpass = findViewById(R.id.edit_conf_password);
        url = findViewById(R.id.edit_broker_url);
        port = findViewById(R.id.edit_port);

        buttonRegister = findViewById(R.id.button_register_new);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass.getText().toString().equals(cpass.getText().toString())){
                    user = new User();
                    user.setUsername(uname.getText().toString());
                    user.seteMail(mail.getText().toString());
                    user.setPassword(pass.getText().toString());
                    user.setBrokerURL(url.getText().toString());
                    user.setPort(Integer.parseInt(port.getText().toString()));
                    if (usernames.contains(uname.getText().toString())){
                        Toast.makeText(RegisterActivity.this, "Username Already Exist", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else if (mails.contains(mail.getText().toString())){
                        Toast.makeText(RegisterActivity.this, "Email Already in Use", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else{
                        database.mainDAO().insert(user);
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }


}
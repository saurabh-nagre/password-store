package com.master.passwordstore2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;
import java.util.Random;

public class AddPassword extends AppCompatActivity {

    Toolbar toolbar;

    Button addButton;
    Button generateButton;

    EditText appnameET;
    EditText passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_password);

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        addButton = findViewById(R.id.addPassbutton);
        appnameET = findViewById(R.id.addnameEdittext);
        passwordET = findViewById(R.id.passwordEditText);
        generateButton = findViewById(R.id.generatepasswordbutton);

        DBHelper dbHelper = new DBHelper(this);

        addButton.setOnClickListener(v -> {
            finishAffinity();
            String appname = appnameET.getText().toString();
            String pass = passwordET.getText().toString();

            dbHelper.insert(appname,pass);

            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();

        });


        generateButton.setOnClickListener(v ->{
            String pass = setPassword();
            passwordET.setText(pass,null);

        });
    }

    public String setPassword(){
        String pass;
        Random random = new Random();
        int value;

        StringBuilder passBuilder = new StringBuilder();
        for(int i = 0; i<15; i++){
           value = random.nextInt(74)+48;
           passBuilder.append(((char) value));
        }

        pass = passBuilder.toString();
        return pass;
    }
}

package com.master.passwordstore2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UpdatePassword extends AppCompatActivity {

    Intent intent;
    String appname;
    String password;
    DBHelper dbHelper;
    EditText editText1,editText2;

    Button updateButton;
    Button generateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_password);

        intent = getIntent();
        appname = intent.getStringExtra("appName");
        password = intent.getStringExtra("Password");

        editText1 = findViewById(R.id.addnameUpdatetext);
        editText2 = findViewById(R.id.passwordUpdateText);

        editText1.setText(appname);
        editText2.setText(password);



        dbHelper = new DBHelper(this);

        updateButton = findViewById(R.id.updatebutton);
        generateButton = findViewById(R.id.generatebutton);

        updateButton.setOnClickListener(v->{

            finishAffinity();
            String newappname = editText1.getText().toString();
            String newPassword = editText2.getText().toString();
            dbHelper.update(appname,newappname,newPassword);

            Intent switcher= new Intent(this,MainActivity.class);
            startActivity(switcher);
            finish();
        });

        generateButton.setOnClickListener(v -> {
            AddPassword obj = new AddPassword();

            editText2.setText(obj.setPassword());
        });
    }
}

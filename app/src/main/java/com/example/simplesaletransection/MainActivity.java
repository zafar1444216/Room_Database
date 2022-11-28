package com.example.simplesaletransection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Query;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {
    Button register;
    Button exit;
    TextView notRegister;
    EditText userName,userEmail,userPassward;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exit = findViewById(R.id.exit);
        notRegister = findViewById(R.id.goToLogin);
        userName = findViewById(R.id.username);
        userEmail = findViewById(R.id.editTextTextPersonName);
        userPassward = findViewById(R.id.password);
        UserTable userTable=new UserTable();

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        register = (Button) findViewById(R.id.signupbtn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameText = userName.getText().toString();
                String useremailText = userEmail.getText().toString();
                String passwordText = userPassward.getText().toString();
                if (usernameText.isEmpty() || useremailText.isEmpty() || passwordText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter detail", Toast.LENGTH_LONG).show();
                } else {
                   userTable.setUserEmail(useremailText);
                   userTable.setUserPassword(passwordText);
                   userTable.setUserName(usernameText);
                    DatabaseHelper.getInstance(getApplicationContext()).getAppDatabase().userDao().insertUser(userTable);
                    Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                    startActivity(intent);
                }
            }
        });
        notRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
            }
        });
}}
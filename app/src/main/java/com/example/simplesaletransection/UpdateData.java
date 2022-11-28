package com.example.simplesaletransection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateData extends AppCompatActivity {
    Button register;
    Button exit;
    TextView notRegister;
    EditText userName,userEmail,userPassward;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        exit = findViewById(R.id.exit);
        notRegister = findViewById(R.id.goToLogin);
        userName = findViewById(R.id.username);
        userEmail = findViewById(R.id.editTextTextPersonName);
        userPassward = findViewById(R.id.password);
        register = (Button) findViewById(R.id.signupbtn);
        UserTable userTable=new UserTable();
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameText = userName.getText().toString();
                String useremailText = userEmail.getText().toString();
                String passwordText = userPassward.getText().toString();
                int id=1;
                if (usernameText.isEmpty() || useremailText.isEmpty() || passwordText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter detail", Toast.LENGTH_LONG).show();
                } else {
                    userTable.setUserEmail(useremailText);
                    userTable.setUserPassword(passwordText);
                    userTable.setUserName(usernameText);
                    DatabaseHelper.getInstance(getApplicationContext()).getAppDatabase().userDao().update(usernameText,useremailText,passwordText,id);
                    Toast.makeText(getApplicationContext(),"updated thank you",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

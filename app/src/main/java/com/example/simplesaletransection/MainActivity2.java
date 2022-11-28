package com.example.simplesaletransection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    ImageView close5;
    Button button5;
    TextView notRegister;
    EditText userName,userEmail,userPassword;
    UserTable userTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        notRegister=findViewById(R.id.next);
        userName=findViewById(R.id.editTextTextPersonName3);
        userEmail=findViewById(R.id.editTextTextPersonName);
        userPassword=findViewById(R.id.editTextTextPersonName2);
        button5 = findViewById(R.id.btn);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameText = userName.getText().toString();
                String useremailText = userEmail.getText().toString();
                String passwordText = userPassword.getText().toString();
                if(usernameText.isEmpty() && useremailText.isEmpty() && passwordText.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter detail",Toast.LENGTH_LONG).show();
                }else {
                    /*UserTable userTable=new UserTable();
                    userTable.setUserName(usernameText);
                    userTable.setUserEmail(useremailText);
                    userTable.setUserPassword(passwordText);*/
                    int result=DatabaseHelper.getInstance(getApplicationContext()).getAppDatabase().userDao().login(useremailText,usernameText,passwordText);
                   if(result!=0) {
                       Intent intent1 = new Intent(getApplicationContext(), MainActivity3.class);
                       intent1.putExtra("user_email",useremailText);
                       startActivity(intent1);
                   }else{
                       // Toast message need to print
                       Toast.makeText(getApplicationContext(), "Please enter correct details", Toast.LENGTH_LONG).show();
                   }
                }
            }
        });
        notRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}


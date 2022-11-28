package com.example.simplesaletransection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Update;

public class MainActivity3 extends AppCompatActivity {
    Button exit,button,button1,button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        button2 = findViewById(R.id.button2);
        exit = findViewById(R.id.exit);
        button1 =findViewById(R.id.button1);
        String user_email = getIntent().getStringExtra("user_email");

        button = findViewById(R.id.button);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), CardDetailActivity.class);
               // Toast.makeText(getApplicationContext(),"MainActivity3",Toast.LENGTH_LONG).show();
                startActivity(intent3);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(getApplicationContext(), DeleteData.class);
                // Toast.makeText(getApplicationContext(),"MainActivity3",Toast.LENGTH_LONG).show();
                intent4.putExtra("user_email",user_email);
                startActivity(intent4);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), UpdateData.class);
                // Toast.makeText(getApplicationContext(),"MainActivity3",Toast.LENGTH_LONG).show();
                startActivity(intent3);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(getApplicationContext(), AlarmManager.class);
                startActivity(intent5);
            }
        });

    }
}
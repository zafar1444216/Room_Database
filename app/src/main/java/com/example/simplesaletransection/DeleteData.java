package com.example.simplesaletransection;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_row2);

        String user_email = getIntent().getStringExtra("user_email");

        DatabaseHelper.getInstance(getApplicationContext()).getAppDatabase().userDao().delete(user_email);
        String deleted_toast = "deleted " + user_email + " thank you";
        Toast.makeText(getApplicationContext(), deleted_toast, Toast.LENGTH_LONG).show();
    }
}


package com.example.simplesaletransection;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.simplesaletransection.databinding.ActivityAlarmManagerBinding;
import com.example.simplesaletransection.databinding.ActivityMainBinding;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class AlarmManager extends AppCompatActivity {

    private ActivityAlarmManagerBinding binding;
    private MaterialTimePicker picker;
    private Calendar calendar;
    private android.app.AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlarmManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createNotificationChannel();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showTimePicker();
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarm();

            }
        });

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarm();

            }
        });
    }

    private void cancelAlarm() {
        Intent intent = new Intent(this,AlarmReciver.class);

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        if (alarmManager == null){

            alarmManager = (android.app.AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }

        alarmManager.cancel(pendingIntent);
        Toast.makeText(this,"Alarm Canceled",Toast.LENGTH_LONG).show();
    }

    private void setAlarm() {
        alarmManager = (android.app.AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this,AlarmReciver.class);

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        alarmManager.setInexactRepeating(android.app.AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), android.app.AlarmManager.INTERVAL_DAY,pendingIntent);

        Toast.makeText(this,"Alarm set Successfully",Toast.LENGTH_LONG).show();
    }

    private void showTimePicker(){
        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Alarm Time")
                .build();

        picker.show(getSupportFragmentManager(),"foxandroid");
        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (picker.getHour() > 12){

                    binding.button.setText((picker.getHour()-12)+" : "+picker.getMinute()+"PM");
                    time=binding.button.getText().toString();
                }else{
                    binding.button.setText(picker.getHour()+" : "+picker.getMinute()+"AM");
                    time=binding.button.getText().toString();
                }
                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,picker.getHour());
                calendar.set(Calendar.MINUTE,picker.getMinute());
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);
                UserAlarm userAlarm= new UserAlarm();
                userAlarm.setUserTime(time);
                DatabaseHelper.getInstance(getApplicationContext()).getAppDatabase().userAlramDao().addTime(userAlarm);
            }}
        );

    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "foxandroidReminderchannel";
            String description ="Channel For Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("foxandroid",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}


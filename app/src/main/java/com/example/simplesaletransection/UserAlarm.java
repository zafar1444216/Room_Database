package com.example.simplesaletransection;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserAlarm")
public class UserAlarm {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "usertime")
    private String userTime;

    public UserAlarm() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserTime() {
        return userTime;
    }

    public void setUserTime(String userTime) {
        this.userTime = userTime;
    }
}

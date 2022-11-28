package com.example.simplesaletransection;

import androidx.room.Dao;
import androidx.room.Insert;
@Dao
public interface UserAlramDao {

    @Insert
    void addTime(UserAlarm userAlarm);
}

package com.example.simplesaletransection;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserTable.class,ResponseEntity.class,UserAlarm.class},version = 1)
public abstract class MyDatabase extends RoomDatabase {
    private static final String dbName= "user";
    private static MyDatabase myDatabase;

    public static synchronized MyDatabase getUserDatabase(Context context){

        if(myDatabase==null){
            myDatabase = Room.databaseBuilder(context, MyDatabase.class, dbName).fallbackToDestructiveMigration().build();
        }
        return myDatabase;
    }


    public abstract UserDao userDao();
    public abstract ResponseDataDao responseDataDao();
    public abstract UserAlramDao userAlramDao();
}

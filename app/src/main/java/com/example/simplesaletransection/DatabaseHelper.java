package com.example.simplesaletransection;

import android.content.Context;

import androidx.room.Room;

public class DatabaseHelper {
    private Context mCtx;
    private static DatabaseHelper mInstance;

    //our app database object
    private MyDatabase appDatabase;

    private DatabaseHelper(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = Room.databaseBuilder(mCtx, MyDatabase.class, "user").allowMainThreadQueries().build();
    }

    public static synchronized DatabaseHelper getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(mCtx);
        }
        return mInstance;
    }

    public MyDatabase getAppDatabase() {
        return appDatabase;
    }
}

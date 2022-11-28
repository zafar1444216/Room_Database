package com.example.simplesaletransection;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.jar.Attributes;

@Entity(tableName = "UserTable")
public class UserTable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "userName")
    private String userName;
    @ColumnInfo(name = "userEmail")
    private String userEmail;
    @ColumnInfo(name = "userPassword")
    private String userPassword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}

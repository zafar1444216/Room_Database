package com.example.simplesaletransection;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {
    @Insert
    void insertUser(UserTable userTable);


//    @Query("SELECT EXISTS (SELECT * from UserTable where userEmail=:useremailText)")
//    boolean is_taken(String userEmail);


    @Query("SELECT * from UserTable where userEmail=:userEmail AND userPassword=:userPassword AND userName=:useName")
    int login(String userEmail,String useName,String userPassword);



    @Update
    void updateUser(UserTable userTable);

    @Query("UPDATE UserTable SET userEmail = :userEmail, userPassword = :userPassword, userName = :useName where id=:id")
    int update(String userEmail,String useName,String userPassword,int id);

    @Delete
    public void deleteUser(UserTable userTable);
    @Query("DELETE  from UserTable where userEmail=:userEmail")
    int delete(String userEmail);






}

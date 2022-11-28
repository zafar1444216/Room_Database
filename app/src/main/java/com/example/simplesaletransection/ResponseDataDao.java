package com.example.simplesaletransection;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ResponseDataDao {

    @Insert
    void registerResponseData(ResponseEntity responseEntity);

    @Query("Select * from responsedata")
    List<ResponseEntity> getAllData();



}

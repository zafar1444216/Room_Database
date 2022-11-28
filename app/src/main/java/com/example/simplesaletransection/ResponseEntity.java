package com.example.simplesaletransection;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName ="responsedata")
public class ResponseEntity {


    @PrimaryKey(autoGenerate = true)
    private Integer id;
    @ColumnInfo(name="f0")
    int f0;
    @ColumnInfo(name="f2")
    int f2;
    @ColumnInfo(name="f3")
    int f3;
    @ColumnInfo(name="f4")
    int f4;
    @ColumnInfo(name="f11")
    int f11;
    @ColumnInfo(name="f12")
    int f12;
    @ColumnInfo(name="f13")
    int f13;
    @ColumnInfo(name="f25")
    int f25;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getF0() {
        return f0;
    }

    public void setF0(int f0) {
        this.f0 = f0;
    }

    public int getF2() {
        return f2;
    }

    public void setF2(int f2) {
        this.f2 = f2;
    }

    public int getF3() {
        return f3;
    }

    public void setF3(int f3) {
        this.f3 = f3;
    }

    public int getF4() {
        return f4;
    }

    public void setF4(int f4) {
        this.f4 = f4;
    }

    public int getF11() {
        return f11;
    }

    public void setF11(int f11) {
        this.f11 = f11;
    }

    public int getF12() {
        return f12;
    }

    public void setF12(int f12) {
        this.f12 = f12;
    }

    public int getF13() {
        return f13;
    }

    public void setF13(int f13) {
        this.f13 = f13;
    }

    public int getF25() {
        return f25;
    }

    public void setF25(int f25) {
        this.f25 = f25;
    }

    public String getF37() {
        return f37;
    }

    public void setF37(String f37) {
        this.f37 = f37;
    }

    public String getF38() {
        return f38;
    }

    public void setF38(String f38) {
        this.f38 = f38;
    }

    public String getF39() {
        return f39;
    }

    public void setF39(String f39) {
        this.f39 = f39;
    }

    public String getF42() {
        return f42;
    }

    public void setF42(String f42) {
        this.f42 = f42;
    }

    @ColumnInfo(name="f37")
    String f37;
    @ColumnInfo(name="f38")
    String f38;
    @ColumnInfo(name="f39")
    String f39;
    @ColumnInfo(name="f42")
    String f42;






    public ResponseEntity(){
        super();
    }

    public ResponseEntity(Integer id, int f0, int f2, int f3,int f4, int f11, int f12, int f13, int f25, String f37, String f38, String f39, String f42) {
        super();
        this.id = id;
        this.f0 = f0;
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;
        this.f11 = f11;
        this.f12 = f12;
        this.f13 = f13;
        this.f25 = f25;
        this.f37 = f37;
        this.f38 = f38;
        this.f39 = f39;
        this.f42 = f42;
    }





}

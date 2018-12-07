package com.example.carlos.aplicacionnatureyou;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table productos (idProducto integer primary key, descripcion text, nombre text, " +
                "precio double,categoria text)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists productos");
        db.execSQL("create table productos (idProducto integer primary key, descripcion text, nombre text, " +
                "precio double,categoria text)");


    }
}



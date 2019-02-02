package com.example.elder.app2prova;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyCrud.db";
    private static final int DATABASE_VERSION = 1;
    private final String CREATE_TABLE = "CREATE TABLE Tarefa (ID INTEGER PRIMARY KEY AUTOINCREMENT ,DESCRICAO TEXT NOT NULL,   CATEGORIA TEXT, PRIORIDADE INTEGER NOT NULL, STATUS BOOLEAN NOT NULL );";
    //private final String CREATE_TABLE = "DROP DATABASE Tarefas;";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
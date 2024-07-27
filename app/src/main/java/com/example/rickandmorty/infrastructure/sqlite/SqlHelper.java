package com.example.rickandmorty.infrastructure.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqlHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "RickAndMorty.db";
    private static final int DATABASE_VERSION = 1;

    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String TABLE_CREATE_CHARACTER =
            "CREATE TABLE CHARACTERS (" +
            "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Name TEXT, Image TEXT, Created TEXT);";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE_CHARACTER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CHARACTERS");
        onCreate(sqLiteDatabase);
    }
}

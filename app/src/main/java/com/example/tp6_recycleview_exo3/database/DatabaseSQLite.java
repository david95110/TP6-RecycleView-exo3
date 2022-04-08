package com.example.tp6_recycleview_exo3.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseSQLite extends SQLiteOpenHelper {

    public final static int VERSION = 1;

    public final static String CREATE_TABLE =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL);",
                    ContractProvider.Person.TABLE_NAME,
                    ContractProvider.Person.COL_ID,
                    ContractProvider.Person.COL_NAME,
                    ContractProvider.Person.COL_FIRSTNAME);

    public DatabaseSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ContractProvider.Person.TABLE_NAME);
        db.execSQL(CREATE_TABLE);
    }
}

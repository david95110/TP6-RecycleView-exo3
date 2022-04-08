package com.example.tp6_recycleview_exo3.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyContentProvider extends ContentProvider {

    DatabaseSQLite dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseSQLite(getContext(), ContractProvider.DB_NAME,
                null, DatabaseSQLite.VERSION);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] columns, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        long id = extractIdFromUri(uri);

        if (id < 0) {
            return db.query(ContractProvider.Person.TABLE_NAME, columns, selection, selectionArgs,
                    null, null, sortOrder);
        } else {
            return db.query(ContractProvider.Person.TABLE_NAME, columns,
                    ContractProvider.Person.COL_ID + " = " + id,
                    selectionArgs, null, null, sortOrder);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase())
        //SQLiteDatabase db = dbHelper.getWritableDatabase();
        {  // autoclose
            long id = db.insertOrThrow(ContractProvider.Person.TABLE_NAME, null, values);

            if (id == -1) {
                throw new RuntimeException("insert/insertrOrThrow failed");
            }

            return ContentUris.withAppendedId(uri, id);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            return db.delete(ContractProvider.Person.TABLE_NAME, selection, selectionArgs);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase())
        //SQLiteDatabase db = dbHelper.getWritableDatabase();

        {
            return db.update(ContractProvider.Person.TABLE_NAME, values, selection, selectionArgs);
        }
    }

    public static int extractIdFromUri(Uri uri) {
        String lastPathSegment = uri.getLastPathSegment();

        if (lastPathSegment == null) {
            return -1;
        }

        return Integer.parseInt(lastPathSegment);
    }
}

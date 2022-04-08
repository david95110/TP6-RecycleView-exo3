package com.example.tp6_recycleview_exo3.database;

import android.net.Uri;

public class ContractProvider {

    public final static String AUTHORITY = "com.example.tp6_recycleview_exo3.database.MyContentProvider";
    public final static String DB_NAME = "database.db";

    public static class Person {
        public final static String TABLE_NAME = "table_person";
        public final static String COL_ID = "Id";
        public final static String COL_NAME = "Name";
        public final static String COL_FIRSTNAME = "Firstname";
        public final static Uri CONTENTURI = Uri.parse("content://" + AUTHORITY);
    }
}

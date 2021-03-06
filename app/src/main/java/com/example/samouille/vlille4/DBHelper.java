package com.example.samouille.vlille4;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "vlille.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_STUDENT = "CREATE TABLE " + VLille.TABLE  + "("
                + VLille.COLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + VLille.COLUMN_NOM + " TEXT, "
                + VLille.COLUMN_COMMUNE + " TEXT, "
                + VLille.COLUMN_ADRESSE + " TEXT, "
                + VLille.COLUMN_NBVELOSDISPO + " TEXT, "
                + VLille.COLUMN_NBPLACESDISPO + " TEXT, "
                + VLille.COLUMN_LATITUDE + " DOUBLE, "
                + VLille.COLUMN_LONGITUDE + " DOUBLE )";

        db.execSQL(CREATE_TABLE_STUDENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + VLille.TABLE);

        // Create tables again
        onCreate(db);

    }

}


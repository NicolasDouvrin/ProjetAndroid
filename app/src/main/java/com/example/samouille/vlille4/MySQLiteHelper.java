package com.example.samouille.vlille4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "VLille";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_COMMUNE = "commune";
    public static final String COLUMN_ADRESSE = "adresse";
    public static final String COLUMN_NBVELOSDISPO = "nbVelosDispo";
    public static final String COLUMN_NBPLACESDISPO = "nbPlacesDispo";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    private static final String DATABASE_NAME = "VLille.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_VLILLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    // Commande sql pour la création de la base de données
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME +"("
            + COLUMN_ID + "integer primary key autoincrement,"
            + COLUMN_COMMUNE + "text"
            + COLUMN_ADRESSE + "text"
            + COLUMN_NBVELOSDISPO + "integer"
            + COLUMN_NBPLACESDISPO + "integer"
            + COLUMN_LATITUDE + "double"
            + COLUMN_LONGITUDE + "double"
            +");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_VLILLE_DROP);
        onCreate(db);
    }
}

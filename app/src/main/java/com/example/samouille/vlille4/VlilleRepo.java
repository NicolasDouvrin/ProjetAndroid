package com.example.samouille.vlille4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class VlilleRepo {

    private DBHelper dbHelper;

    public VlilleRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(VLille vlille) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(vlille.COLUMN_NOM, vlille.nom);
        values.put(vlille.COLUMN_COMMUNE, vlille.commune);
        values.put(vlille.COLUMN_ADRESSE, vlille.adresse);
        values.put(vlille.COLUMN_NBVELOSDISPO, vlille.nbVelosDispo);
        values.put(vlille.COLUMN_NBPLACESDISPO, vlille.nbPlacesDispo);
        values.put(vlille.COLUMN_LATITUDE, vlille.latitude);
        values.put(vlille.COLUMN_LONGITUDE, vlille.longitude);

        // Inserting Row
        long student_Id = db.insert(VLille.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) student_Id;
    }

    public void delete(int vlille_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(VLille.TABLE, VLille.COLUMN_ID + "= ?", new String[]{String.valueOf(vlille_Id)});
        db.close(); // Closing database connection
    }

    public void update(VLille vlille) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(vlille.COLUMN_NOM, vlille.nom);
        values.put(vlille.COLUMN_COMMUNE, vlille.commune);
        values.put(vlille.COLUMN_ADRESSE, vlille.adresse);
        values.put(vlille.COLUMN_NBVELOSDISPO, vlille.nbVelosDispo);
        values.put(vlille.COLUMN_NBPLACESDISPO, vlille.nbPlacesDispo);
        values.put(vlille.COLUMN_LATITUDE, vlille.latitude);
        values.put(vlille.COLUMN_LONGITUDE, vlille.longitude);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(VLille.TABLE, values, VLille.COLUMN_ID + "= ?", new String[]{String.valueOf(vlille.id)});
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getVlilleList() {

        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                VLille.COLUMN_ID + "," +
                VLille.COLUMN_NOM + "," +
                VLille.COLUMN_COMMUNE + "," +
                VLille.COLUMN_ADRESSE + "," +
                VLille.COLUMN_NBVELOSDISPO + "," +
                VLille.COLUMN_NBPLACESDISPO + "," +
                VLille.COLUMN_LATITUDE + "," +
                VLille.COLUMN_LONGITUDE +
                " FROM " + VLille.TABLE;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> vlilleList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> vlille = new HashMap<String, String>();
                vlille.put("id", cursor.getString(cursor.getColumnIndex(VLille.COLUMN_ID)));
                vlille.put("nom", cursor.getString(cursor.getColumnIndex(VLille.COLUMN_NOM)));
                vlilleList.add(vlille);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return vlilleList;

    }

    public VLille getVlilleById(int Id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                VLille.COLUMN_ID + "," +
                VLille.COLUMN_NOM + "," +
                VLille.COLUMN_COMMUNE + "," +
                VLille.COLUMN_ADRESSE + "," +
                VLille.COLUMN_NBVELOSDISPO + "," +
                VLille.COLUMN_NBPLACESDISPO + "," +
                VLille.COLUMN_LATITUDE + "," +
                VLille.COLUMN_LONGITUDE + "," +
                " FROM " + VLille.TABLE
                + " WHERE " +
                VLille.COLUMN_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        VLille vlille = new VLille();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});

        if (cursor.moveToFirst()) {
            do {
                vlille.id = cursor.getInt(cursor.getColumnIndex(VLille.COLUMN_ID));
                vlille.nom = cursor.getString(cursor.getColumnIndex(VLille.COLUMN_NOM));
                vlille.commune = cursor.getString(cursor.getColumnIndex(VLille.COLUMN_COMMUNE));
                vlille.adresse = cursor.getString(cursor.getColumnIndex(VLille.COLUMN_ADRESSE));
                vlille.nbVelosDispo = cursor.getInt(cursor.getColumnIndex(VLille.COLUMN_NBVELOSDISPO));
                vlille.nbPlacesDispo = cursor.getInt(cursor.getColumnIndex(VLille.COLUMN_NBPLACESDISPO));
                vlille.latitude = cursor.getDouble(cursor.getColumnIndex(VLille.COLUMN_LATITUDE));
                vlille.longitude = cursor.getDouble(cursor.getColumnIndex(VLille.COLUMN_LONGITUDE));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return vlille;
    }
}
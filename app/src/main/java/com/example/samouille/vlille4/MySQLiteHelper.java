package com.example.samouille.vlille4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "VLille";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOM = "nom";
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
            + COLUMN_NOM + "text"
            + COLUMN_COMMUNE + "text"
            + COLUMN_ADRESSE + "text"
            + COLUMN_NBVELOSDISPO + "integer"
            + COLUMN_NBPLACESDISPO + "integer"
            + COLUMN_LATITUDE + "double"
            + COLUMN_LONGITUDE + "double"
            +");";

    public MySQLiteHelper(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_VLILLE_DROP);
        onCreate(db);
    }

    // Adding new vlille
    public void addVlille(VLille vlille) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOM,vlille.getNom());
        values.put(COLUMN_COMMUNE,vlille.getCommune());
        values.put(COLUMN_ADRESSE, vlille.getAdresse());
        values.put(COLUMN_NBVELOSDISPO,vlille.getNbVelosDispo());
        values.put(COLUMN_NBPLACESDISPO,vlille.getNbPlacesDispo());
        values.put(COLUMN_LATITUDE,vlille.getLatitude());
        values.put(COLUMN_LONGITUDE,vlille.getLongitude());
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    // Getting one vlille
    public VLille getVlille(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID,
                COLUMN_NOM, COLUMN_COMMUNE, COLUMN_ADRESSE, COLUMN_NBVELOSDISPO, COLUMN_NBPLACESDISPO, COLUMN_LATITUDE, COLUMN_LONGITUDE}, COLUMN_ID + "=?",
        new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        VLille vlille = new VLille(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                Integer.parseInt(cursor.getString(4)),
                Integer.parseInt(cursor.getString(5)),
                Double.parseDouble(cursor.getString(6)),
                Double.parseDouble(cursor.getString(7)));
    // return vlille
        return vlille;
    }

    // Getting All vlille
    public ArrayList<VLille> getAllVlille() {
        ArrayList<VLille> ListVlille = new ArrayList<VLille>();
        // Select All Query
        String selectQuery = "SELECT * FROM" + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                VLille vlille = new VLille(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                Integer.parseInt(cursor.getString(4)),
                Integer.parseInt(cursor.getString(5)),
                Double.parseDouble(cursor.getString(6)),
                Double.parseDouble(cursor.getString(7)));

                // Adding vlille to list
                ListVlille.add(vlille);
            } while (cursor.moveToNext());
        }
        // return vlille list
        return ListVlille;
    }

/*

    // Getting shops Count
    public int getShopsCount() {
        String countQuery = “SELECT * FROM ” + TABLE_SHOPS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

    // return count
        return cursor.getCount();
    }
    // Updating a shop
    public int updateShop(Shop shop) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, shop.getName());
        values.put(KEY_SH_ADDR, shop.getAddress());

    // updating row
        return db.update(TABLE_SHOPS, values, KEY_ID + ” = ?”,
        new String[]{String.valueOf(shop.getId())});
    }

    // Deleting a shop
    public void deleteShop(Shop shop) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SHOPS, KEY_ID + ” = ?”,
        new String[] { String.valueOf(shop.getId()) });
        db.close();
    }

*/

}


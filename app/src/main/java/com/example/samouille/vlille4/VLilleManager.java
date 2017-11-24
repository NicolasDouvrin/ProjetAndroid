package com.example.samouille.vlille4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class VLilleManager {

    public static final String TABLE_NAME = "VLille";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_COMMUNE = "commune";
    public static final String COLUMN_ADRESSE = "adresse";
    public static final String COLUMN_NBVELOSDISPO = "nbVelosDispo";
    public static final String COLUMN_NBPLACESDISPO = "nbPlacesDispo";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String DATABASE_CREATE = "create table "
            + TABLE_NAME +"("
            + COLUMN_ID + "integer primary key autoincrement,"
            + COLUMN_COMMUNE + "text"
            + COLUMN_ADRESSE + "text"
            + COLUMN_NBVELOSDISPO + "integer"
            + COLUMN_NBPLACESDISPO + "integer"
            + COLUMN_LATITUDE + "double"
            + COLUMN_LONGITUDE + "double"
            +");";
    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public VLilleManager(Context context)
    {
        maBaseSQLite = MySQLite.getInstance(context);
    }

    public void open()
    {
        //on ouvre la table en lecture/écriture
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close()
    {
        //on ferme l'accès à la BDD
        db.close();
    }

    public long addAnimal(VLille vlille) {
        // Ajout d'un enregistrement dans la table
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMMUNE,vlille.getCommune());
        values.put(COLUMN_ADRESSE, vlille.getAdresse());
        values.put(COLUMN_NBVELOSDISPO,vlille.getNbVelosDispo());
        values.put(COLUMN_NBPLACESDISPO,vlille.getNbPlacesDispo());
        values.put(COLUMN_LATITUDE,vlille.getLatitude());
        values.put(COLUMN_LONGITUDE,vlille.getLongitude());
        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

/*
    public int modAnimal(VLille vlille) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(COL, animal.getNom_animal());

        String where = KEY_ID_ANIMAL+" = ?";
        String[] whereArgs = {animal.getId_animal()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int supAnimal(Animal animal) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_ANIMAL+" = ?";
        String[] whereArgs = {animal.getId_animal()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }
*/

    public VLille getAnimal(int id) {
        // Retourne l'animal dont l'id est passé en paramètre
        VLille v=new VLille(0,"","",0,0,0,0);
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_ID+"="+id, null);
        if (c.moveToFirst()) {
            v.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
            v.setCommune(c.getString(c.getColumnIndex(COLUMN_COMMUNE)));
            v.setAdresse(c.getString(c.getColumnIndex(COLUMN_ADRESSE)));
            v.setNbPlacesDispo(c.getInt(c.getColumnIndex(COLUMN_NBPLACESDISPO)));
            v.setNbVelosDispo(c.getInt(c.getColumnIndex(COLUMN_NBVELOSDISPO)));
            v.setLatitude(c.getInt(c.getColumnIndex(COLUMN_LATITUDE)));
            v.setLongitude(c.getInt(c.getColumnIndex(COLUMN_LONGITUDE)));
            c.close();
        }
        return v;
    }

    public Cursor getAnimaux() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

} // class AnimalManager
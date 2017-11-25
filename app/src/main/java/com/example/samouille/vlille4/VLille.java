package com.example.samouille.vlille4;

public class VLille {
    // Labels table name
    public static final String TABLE = "Vlille";

    // Labels Table Columns names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOM = "nom";
    public static final String COLUMN_COMMUNE = "commune";
    public static final String COLUMN_ADRESSE = "adresse";
    public static final String COLUMN_NBVELOSDISPO = "nbVelosDispo";
    public static final String COLUMN_NBPLACESDISPO = "nbPlacesDispo";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    // property help us to keep data
    public int id;
    public String nom;
    public String commune;
    public String adresse;
    public int nbVelosDispo;
    public int nbPlacesDispo;
    public double latitude;
    public double longitude;
}

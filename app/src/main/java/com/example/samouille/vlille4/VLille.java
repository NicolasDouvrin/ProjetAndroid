package com.example.samouille.vlille4;

public class VLille {
    private int id;
    private String adresse;
    private String commune;
    private int nbPlacesDispo;
    private int nbVelosDispo;
    private double latitude;
    private double longitude;

    public VLille(int id, String adresse, String commune, int nbPlacesDispo, int nbVelosDispo, double latitude, double longitude) {
        this.id=id;
        this.adresse = adresse;
        this.commune = commune;
        this.nbPlacesDispo = nbPlacesDispo;
        this.nbVelosDispo = nbVelosDispo;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public int getNbPlacesDispo() {
        return nbPlacesDispo;
    }

    public void setNbPlacesDispo(int nbPlacesDispo) {
        this.nbPlacesDispo = nbPlacesDispo;
    }

    public int getNbVelosDispo() {
        return nbVelosDispo;
    }

    public void setNbVelosDispo(int nbVelosDispo) {
        this.nbVelosDispo = nbVelosDispo;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

package com.example.nicol.vlille;

/**
 * Created by Nico on 21/11/2017.
 */

public class Vlille {
    private String nom;
    private String etat;
    private String commune;

     public Vlille(String nom, String etat, String commune) {
        this.nom = nom;
        this.etat = etat;
        this.commune = commune;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }
}

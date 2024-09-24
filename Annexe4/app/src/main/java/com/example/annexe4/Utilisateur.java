package com.example.annexe4;

import java.io.Serializable;

public class Utilisateur implements Serializable {
    private String prenom;
    private String nom;

    public Utilisateur(String prenom, String nom) {
        this.prenom = prenom;
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }
}

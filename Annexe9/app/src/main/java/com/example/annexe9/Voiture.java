package com.example.annexe9;

import java.util.List;

public class Voiture {

    private String nom;
    private int prix;



    private List<Voiture> voitures;
    public List<Voiture> getVoitures() {
        return voitures;
    }




    public Voiture(Builder b){
        this.nom = b.nom;
        this.prix = b.prix;
    }

    public String getNom() {
        return nom;
    }

    public int getPrix() {
        return prix;
    }

    public static class Builder{
        private String nom;
        private int prix;


        public Builder setNom(String nom){
            this.nom = nom;
            return this;
        }

        public Builder setPrix(int prix){
            this.prix = prix;
            return this;
        }

        public Voiture build(){
            return new Voiture(this);
        }
    }
}
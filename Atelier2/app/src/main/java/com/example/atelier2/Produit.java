package com.example.atelier2;

public class Produit {
    private String nom;
    private double prix;

    public Produit(Builder builder) {
        this.nom = builder.nom;
        this.prix = builder.prix;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

    public static class Builder {
        private String nom;
        private double prix;

        public Builder setNom(String nom){
            this.nom = nom;
            return this;
        }

        public Builder setPrix(double prix){
            this.prix = prix;
            return this;
        }

        public Produit build() {
            return new Produit(this);
        }
    }
}
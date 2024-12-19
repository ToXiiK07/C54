package com.example.pratique_examen;

import java.io.Serializable;

public class Personne implements Serializable {

    String nom;
    int age;

    public Personne(String nom, int age) {
        this.nom = nom;
        this.age = age;
    }

    public String getNom() {
        return nom;
    }

    public int getAge() {
        return age;
    }
}
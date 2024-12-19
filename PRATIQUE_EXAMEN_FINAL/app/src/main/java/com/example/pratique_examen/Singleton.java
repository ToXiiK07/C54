package com.example.pratique_examen;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Singleton {

    private static Singleton instance;
    private Context context;

    private Personne userData;

    private Singleton(Context context) {
        this.userData = new Personne("", 0);
        this.context = context;
    }

    public static Singleton getInstance(Context context){
        if(instance == null){
            instance = new Singleton(context);
        }
        return instance;
    }

    public void setUserData(String nom, int age) {
        this.userData = new Personne(nom, age);
    }

    public Personne getUserData() {
        return userData;
    }

    // Sérialisation
    public void serialiserListe() throws Exception{
        try (FileOutputStream fos = context.openFileOutput("fichier.ser", MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(userData);
        }
    }

    public void deserialiserListe() throws Exception {
        try (FileInputStream fis = context.openFileInput("fichier.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            userData = (Personne) ois.readObject();
        }
    }
}


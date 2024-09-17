package com.example.annexe3b_serialisation;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

public class Singleton {

    private static Singleton instance;
    private Hashtable<String, Integer> son;
    private Context context;
    private int sonnerieProgress;
    private int mediaProgress;
    private int notifProgress;

    private Singleton(Context context) {
        son = new Hashtable<>();
        this.context = context;
    }

    public static Singleton getInstance(Context context){
        if(instance == null){
            instance = new Singleton(context);
        }
        return instance;
    }

    public void setSeekBar(int sonnerieProgress, int mediaProgress, int notifProgress) {
        this.sonnerieProgress = sonnerieProgress;
        this.mediaProgress = mediaProgress;
        this.notifProgress = notifProgress;
    }

    public int getSonnerieProgress() {
        return sonnerieProgress;
    }

    public int getMediaProgress() {
        return mediaProgress;
    }

    public int getNotifProgress() {
        return notifProgress;
    }

    public void ajouterSon(String nom, int valeur) {
        son.put(nom, valeur);
    }

    // Sérialisation
    public void serialiserListe() throws Exception{
        try (FileOutputStream fos = context.openFileOutput("fichier.ser", MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(son);
            oos.writeInt(sonnerieProgress);
            oos.writeInt(mediaProgress);
            oos.writeInt(notifProgress);
        }
    }

    public void deserialiserListe() throws Exception {
        try (FileInputStream fis = context.openFileInput("fichier.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            son = (Hashtable<String, Integer>) ois.readObject();
            sonnerieProgress = ois.readInt();
            mediaProgress = ois.readInt();
            notifProgress = ois.readInt();
        }
    }
}

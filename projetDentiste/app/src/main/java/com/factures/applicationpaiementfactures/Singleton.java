package com.factures.applicationpaiementfactures;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
public class Singleton {

    private static Singleton instance;
    private Context context;
    public ArrayList<Dent> listeDent;

    private Singleton(Context context){
        listeDent = new ArrayList<>();
        this.context = context;
    }

    public static Singleton getInstance(Context context){
        if(instance == null){
            instance = new Singleton(context);
        }
        return instance;
    }

    public void ajouterDent(Dent dent){
        listeDent.add(dent);
    }

    public void setListeDent(ArrayList<Dent> listeDent){
        this.listeDent = listeDent;
    }

    public void serialiserListe() throws Exception{
        // try-with -resource
        try(
                FileOutputStream fos = context.openFileOutput("fichier.ser", MODE_PRIVATE); // .ser pour dire que c'est sérialisation
                // buffer special pour objet
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(listeDent);
        }
    }

    public  ArrayList<Dent> deserialiserListe() throws Exception {
        ArrayList<Dent> temp;
        try(FileInputStream fis = context.openFileInput("fichier.ser");
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            temp = (ArrayList<Dent>) ois.readObject();
            return temp;
        }
    }
}
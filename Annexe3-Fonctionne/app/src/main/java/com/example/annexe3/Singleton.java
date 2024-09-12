package com.example.annexe3;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class Singleton {

    private static Singleton instance;
    private ArrayList<Memo> listeMemo;
    private Context context;

    private Singleton(Context context) {
        listeMemo = new ArrayList<>();
        this.context = context;
    }

    public static Singleton getInstance(Context context){
        if(instance == null){
            instance = new Singleton(context);
        }
        return instance;
    }

    public ArrayList<Memo> getListeMemo() {
        return listeMemo;
    }

    public void ajouterMemo(Memo memo){
        listeMemo.add(memo);
    }

    public void setListeMemo(ArrayList<Memo> listeMemo) {
        this.listeMemo = listeMemo;
    }

    public void serialiserListe() throws Exception{
        // try-with -resource
        try(
        FileOutputStream fos = context.openFileOutput("fichier.ser", MODE_PRIVATE); // .ser pour dire que c'est sérialisation
        // buffer special pour objet
        ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(listeMemo);
        }
    }

    public ArrayList<Memo> deserialiserListe() throws Exception {
        try(FileInputStream fis = context.openFileInput("fichier.ser");
            ObjectInputStream ois = new ObjectInputStream(fis)) {
               listeMemo = (ArrayList) ois.readObject();
               return listeMemo;
        }
    }

    public ArrayList<Memo> dedeserialiserListe2() throws Exception {
        ArrayList<Memo> temp = null;
        try(FileInputStream fis = context.openFileInput("fichier.ser");
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            temp = (ArrayList<Memo>) ois.readObject();
            return temp;
        }


    }



}
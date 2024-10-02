package com.example.examen1;

import static android.content.Context.MODE_PRIVATE;
import android.content.Context;
import android.os.Build;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

public class Singleton {

    private static Singleton instance;
    public LocalDate date = LocalDate.now();

    private Context context;

    private Singleton(Context context) {

        this.context = context;
    }

    public static Singleton getInstance(Context context){
        if(instance == null){
            instance = new Singleton(context);
        }
        return instance;
    }

    public void serialiserListe() throws Exception{
        try(
                FileOutputStream fos = context.openFileOutput("fichier.ser", MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(date);
        }
    }

    public LocalDate deserialiserListe() throws Exception {
        try(FileInputStream fis = context.openFileInput("fichier.ser");
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            date = (LocalDate) ois.readObject();
            return date;
        }
    }
}

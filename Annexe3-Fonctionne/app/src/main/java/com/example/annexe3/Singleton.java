package com.example.annexe3;

import android.content.Context;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class Singleton {

    private static Singleton instance;
    private ArrayList<Memo> listeMemo;
    private Context context;

    public static Singleton getInstance(){
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }

    private Singleton() {
       listeMemo = new ArrayList<>();
    }

    public ArrayList<Memo> getListeMemo() {
        return listeMemo;
    }

    public void ajouterMemo(Memo memo){
        listeMemo.add(memo);
    }

}
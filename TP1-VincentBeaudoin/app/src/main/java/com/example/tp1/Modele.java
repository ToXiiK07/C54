package com.example.tp1;

import android.content.Context;

import java.util.List;

public class Modele {
    private Singleton singleton;
    private GestionMusique gestionMusique;

    public Modele(Context context, GestionMusique gestionMusique) {
        this.singleton = Singleton.getInstance(context);
        this.gestionMusique = gestionMusique;
        chargerPlaylist();
    }

    public void chargerPlaylist() {
        singleton.chargerMusique(new MusiqueCallback() {
            @Override
            public void onMusiqueCharger(List<Musique> listeMusique) {
                gestionMusique.music = listeMusique;
            }
        });
    }


    public List<Musique> getListeMusiques() {
        return singleton.getListeMusique();
    }

    public void initialiserMusique() {
        List<Musique> listeMusiques = getListeMusiques();
        gestionMusique.music = listeMusiques;
    }
}



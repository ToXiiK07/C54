package com.example.tp1;

import android.content.Context;

import java.util.List;

public class Modele {
    private Singleton singleton;
    private GestionMusique gestionMusique;

    // constructeur du modèle et qui charge les musiques
    public Modele(Context context, GestionMusique gestionMusique) {
        this.singleton = Singleton.getInstance(context);
        this.gestionMusique = gestionMusique;
        chargerPlaylist();
    }

    public void chargerPlaylist() {
        // qui override la méthode onMusiqueCharger de l'interface MusiqueCallback
        singleton.chargerMusique(new MusiqueCallback() {
            @Override
            public void onMusiqueCharger(List<Musique> listeMusique) {
                gestionMusique.music = listeMusique;
            }
        });
    }

    // récupérer la liste des musiques
    public List<Musique> getListeMusiques() {
        return singleton.getListeMusique();
    }

    public void initialiserMusique() {
        List<Musique> listeMusiques = getListeMusiques();
        gestionMusique.music = listeMusiques;
    }
}



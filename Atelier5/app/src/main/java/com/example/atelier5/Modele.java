package com.example.atelier5;

import android.os.Handler;

//c'est le sujet
public class Modele implements Sujet {

    private int valeur;
    private ObservateurChangement obs; // c'est un observateur, il pourrait en avoir plusieurs


    public Modele() {
      genererChangementValeur();
    }


    // méthode générant un changement de la valeur du sujet,
    // on utilise un aitre thread pour ne pas bloquer l'affichage de l'activité et
    // faire en sorte ainsi que l'observateur du sujet soit null
    // le changement surviendra dans 5000 millisecondes
    public void genererChangementValeur()
    {
        Handler handler = new Handler();
        handler.postDelayed(new Thread() {
            int compteur = 0;
            @Override
            public void run() {
                compteur++;
                setValeur(compteur);
                handler.postDelayed(this, 2000); // changement de valeur
            }
        }, 5000); // la méthode run s'exécutera après un délai de 5000 ms
    }

    public int getValeur() {
        return valeur;
    }


    public void setValeur(int valeur) {

        this.valeur=valeur;
        //changement de l'état du sujet, avertir les observateurs
        avertirObservateurs();
    }


    // méthodes de l'interface Sujet
    @Override
    public void ajouterObservateur(ObservateurChangement obs) {
        this.obs = obs;
    }

    @Override
    public void enleverObservateur(ObservateurChangement obs) {
        obs = null;
    }

    @Override
    public void avertirObservateurs() {
        obs.changement(valeur);
    }
}

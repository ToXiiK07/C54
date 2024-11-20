package com.example.pratiqueexamen;

import java.util.ArrayList;
import java.util.List;

public class Modele implements Sujet {
    private List<Observateur> observateurs;
    private List<Musique> musiques;

    public Modele() {
        observateurs = new ArrayList<>();
        musiques = new ArrayList<>();
    }

    @Override
    public void observateurChangement() {
        for (int i = 0; i < observateurs.size(); i++) {
            observateurs.get(i).musiqueLoader();
        }
    }

    public void ajouterObservateur(Observateur observateur) {
        observateurs.add(observateur);
    }

    public void setMusiques(List<Musique> musiques) {
        this.musiques = musiques;
        observateurChangement();
    }

    public List<Musique> getMusiques() {
        return musiques;
    }
}

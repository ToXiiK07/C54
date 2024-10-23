package com.example.tp1;

import android.net.Uri;

import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import java.util.Random;

import java.util.List;

public class GestionMusique {
    List<Musique> music;
    ExoPlayer exoPlayer;
    int enCours;
    Random random = new Random();

    public GestionMusique(ExoPlayer exoPlayer) {
        this.exoPlayer = exoPlayer;
    }

    public void jouerMusique(int i){
        enCours = i;
        String r = music.get(i).getSource();
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(r));
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.play();
    }

    public void prochaineChanson() {
        if (enCours < music.size() - 1) {
            jouerMusique(enCours + 1);
        } else {
            jouerMusique(0);
        }
    }

    public void ancienneChanson() {
        if (enCours > 0) {
            jouerMusique(enCours - 1);
        }
    }

    public void avancerTemps(int i) {
        long nouvellePosition = exoPlayer.getCurrentPosition() + i;

        long dureeTotale = music.get(enCours).getDuration() * 1000;

        if (nouvellePosition < dureeTotale) {
            exoPlayer.seekTo(nouvellePosition);
        } else {
            exoPlayer.seekTo(dureeTotale);
            jouerMusique(enCours + 1);
        }
    }

    public void reculerTemps(int i){
        long nouvellePosition = exoPlayer.getCurrentPosition() - i;

        if (nouvellePosition < 0) {
            nouvellePosition = 0;
        }

        exoPlayer.seekTo(nouvellePosition);
    }

    public void shuffleMusique(){
        int r = random.nextInt(music.size());

        // ... vérifier si la chanson est terminer

        jouerMusique(r);
    }
}
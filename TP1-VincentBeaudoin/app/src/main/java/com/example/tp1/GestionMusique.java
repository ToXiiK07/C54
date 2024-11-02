package com.example.tp1;

import android.content.Context;
import android.net.Uri;

import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import java.util.List;

public class GestionMusique {
    List<Musique> music;
    ExoPlayer exoPlayer;
    int enCours;

    // récupérer le exoplayer qui est dans le Singleton
    public GestionMusique(Context context) {
        this.exoPlayer = Singleton.getInstance(context).getExoPlayer();
    }

    // pour jouer une musique
    public void jouerMusique(int i){
        enCours = i;
        String r = music.get(i).getSource();
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(r));
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.play();
    }

    // changer de chanson
    public void prochaineChanson() {
        if (enCours < music.size() - 1) {
            jouerMusique(enCours + 1);
        } else {
            jouerMusique(0); // si on appuie sur le bouton et qu'il n'y a plus de chanson, alors retour à la première
        }
    }

    // revenir de une chanson
    public void ancienneChanson() {
        if (enCours > 0) {
            jouerMusique(enCours - 1);
        }
    }

    // avancer de 10 secondes
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

    // reculer de 10 secondes
    public void reculerTemps(int i){
        long nouvellePosition = exoPlayer.getCurrentPosition() - i;

        if (nouvellePosition < 0) {
            nouvellePosition = 0;
        }

        exoPlayer.seekTo(nouvellePosition);
    }

    // shuffle une musique à l'aide d'une méthode de l'Exoplayer
    public void shuffleMusique() {
        if (exoPlayer.getShuffleModeEnabled()) {
            exoPlayer.setShuffleModeEnabled(false);
        } else {
            exoPlayer.setShuffleModeEnabled(true);
            Collections.shuffle(music);
        }
    }

    // repeat une chanson à l'aide d'une méthode de l'Exoplayer
    public void repeterChanson() {
        if(exoPlayer.getRepeatMode() == Player.REPEAT_MODE_ONE) {
            exoPlayer.setRepeatMode(Player.REPEAT_MODE_OFF);
        } else {
            exoPlayer.setRepeatMode(Player.REPEAT_MODE_ONE);
        }
    }

    public List<Musique> getMusic() {
        return music;
    }

    // récupérer la chanson en cours
    public int getEnCours() {
        return enCours;
    }
}
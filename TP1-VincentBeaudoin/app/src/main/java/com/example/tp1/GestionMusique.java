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

    public GestionMusique(Context context) {
        this.exoPlayer = Singleton.getInstance(context).getExoPlayer();
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

    public void shuffleMusique() {
        if (exoPlayer.getShuffleModeEnabled()) {
            exoPlayer.setShuffleModeEnabled(false);
        } else {
            exoPlayer.setShuffleModeEnabled(true);
        }
    }


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

    public int getEnCours() {
        return enCours;
    }
}
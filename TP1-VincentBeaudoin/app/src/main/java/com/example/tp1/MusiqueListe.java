package com.example.tp1;

import android.net.Uri;

import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;

import java.util.List;

public class MusiqueListe {
    List<Musique> music;
    ExoPlayer exoPlayer;
    int currentIndex;

    public MusiqueListe(ExoPlayer exoPlayer) {
        this.exoPlayer = exoPlayer;
    }

    public void jouerMusique(int i){
            currentIndex = i;
            String r = music.get(i).getSource();
            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(r));
            exoPlayer.setMediaItem(mediaItem);
            exoPlayer.prepare();
            exoPlayer.play();
    }

    public void prochaineChanson() {
        if (currentIndex < music.size() - 1) {
            jouerMusique(currentIndex + 1);
        }
    }
    public void ancienneChanson() {
        if (currentIndex > 0) {
            jouerMusique(currentIndex - 1);
        }
    }
}
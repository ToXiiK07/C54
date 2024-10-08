package com.example.atelier4;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.media3.common.MediaItem;

public class MainActivity extends AppCompatActivity {

    //  app:use_controller="false"
    //  si on met à true, on a la barre en bas (avancer, stop, etc...)

    ExoPlayer exoPlayer;
    PlayerView playerView;
    String mp3url = "https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/01_-_Intro_-_The_Way_Of_Waking_Up_feat_Alan_Watts.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerView = findViewById(R.id.player_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        exoPlayer = new ExoPlayer.Builder(this).build();

        playerView.setPlayer(exoPlayer);

        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(mp3url));

        exoPlayer.setMediaItem(mediaItem);

        exoPlayer.prepare();

        exoPlayer.play();
    }
}
package com.example.tp1;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.Player;
import androidx.media3.ui.PlayerView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageButton retourPlaylistButton, retourMusiqueButton, ancienneChansonButton, playPauseButton, nouvelleChansonButton, avancerMusiqueChanson, shuffleButton, repeatButton, parametersButton;
    TextView nomPlaylistText, nomChansonText, nomArtisteText, tempsChansonText, avancementChansonText;
    PlayerView playerView;
    SeekBar dureeChanson;
    Ecouteur ecouteur;
    List<Musique> musiqueList;
    GestionMusique gestionMusique;
    Random random;
    Handler handler;
    Singleton singleton;
    Modele modele;
    AudioManager audioManager;
    private boolean isPlaying = false;
    ActivityResultLauncher<Intent> launcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // les objets
        ecouteur = new Ecouteur();
        random = new Random();
        handler = new Handler();

        // pour gérer le volume
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        gestionMusique = new GestionMusique(this);
        singleton = Singleton.getInstance(this);

        modele = new Modele(MainActivity.this, gestionMusique);
        modele.chargerPlaylist();

        musiqueList = new ArrayList<>(modele.getListeMusiques());
        gestionMusique.music = musiqueList;

        modele.initialiserMusique();

        // si on passe à travers de toutes les musiques, on recommence au début du vecteur
        singleton.getExoPlayer().addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackState == Player.STATE_ENDED) {
                    if (isPlaying) {
                        gestionMusique.prochaineChanson();
                        mettreAJourMusique();
                    }
                }
            }
        });

        // le launcher pour le boomerang
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityCallBack());

        // les boutons
        retourMusiqueButton = findViewById(R.id.retourMusiqueButton);
        retourPlaylistButton = findViewById(R.id.retourPlaylistButton);
        ancienneChansonButton = findViewById(R.id.ancienneChansonButton);
        playPauseButton = findViewById(R.id.playPauseButton);
        nouvelleChansonButton = findViewById(R.id.nouvelleChansonButton);
        avancerMusiqueChanson = findViewById(R.id.avancerMusiqueChanson);
        avancementChansonText = findViewById(R.id.avancementChanson);
        shuffleButton = findViewById(R.id.boutonShuffle);
        repeatButton = findViewById(R.id.boutonRepeat);
        parametersButton = findViewById(R.id.parameterButton);

        // les textViews
        nomPlaylistText = findViewById(R.id.titrePlaylist);
        nomChansonText = findViewById(R.id.nomChanson);
        nomArtisteText = findViewById(R.id.nomArtiste);
        tempsChansonText = findViewById(R.id.tempsChanson);

        // les autres
        dureeChanson = findViewById(R.id.seekBar);
        playerView = findViewById(R.id.player_view);
        playerView.setUseController(false);

        // ajout à l'écouteur
        retourMusiqueButton.setOnClickListener(ecouteur);
        retourPlaylistButton.setOnClickListener(ecouteur);
        ancienneChansonButton.setOnClickListener(ecouteur);
        playPauseButton.setOnClickListener(ecouteur);
        nouvelleChansonButton.setOnClickListener(ecouteur);
        avancerMusiqueChanson.setOnClickListener(ecouteur);
        shuffleButton.setOnClickListener(ecouteur);
        repeatButton.setOnClickListener(ecouteur);
        parametersButton.setOnClickListener(ecouteur);

        // Pour desirialiser la chanson
        if(!singleton.isDejaLoader()) { // vérifie si les chansons ont déjà été loadé
            singleton.chargerMusique(new MusiqueCallback() {
                @Override
                public void onMusiqueCharger(List<Musique> musiques) {
                    try {
                        Hashtable<String, Object> musiqueData = singleton.deserialiserListe();
                        int chanson = (Integer) musiqueData.get("index"); // récupére la chanson
                        long position = (Long) musiqueData.get("position"); // récupére sa position
                        if (chanson >= 0 && chanson < gestionMusique.music.size()) { // vérifie que le vecteur ne soit pas vide
                            // joue la musique et gère tout le UI
                            gestionMusique.jouerMusique(chanson);
                            singleton.getExoPlayer().seekTo(position);
                            playerView.setPlayer(singleton.getExoPlayer());
                            singleton.getExoPlayer().play();

                            isPlaying = true;
                            singleton.setDejaLoader(true);
                            mettreAJourMusique();
                            playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
                            handler.post(updateTemps);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        // Pour récupérer une chanson d'une playlist
        Intent intent = getIntent();
        String nomChanson = intent.getStringExtra("nom_chanson");
        String playlist = intent.getStringExtra("playlist");
        nomPlaylistText.setText(playlist);

        int pos = -1;
        for (int i = 0; i < gestionMusique.music.size(); i++) {
            if (gestionMusique.music.get(i).getTitle().equals(nomChanson)) {
                pos = i; // pour que la position soit recherché avec le nom et soit comparé avec tout les autres titres
                break;
            }
        }

        if (pos != -1) {
            isPlaying = true;
            gestionMusique.enCours = pos;
            gestionMusique.jouerMusique(pos);

            playerView.setPlayer(singleton.getExoPlayer());

            mettreAJourMusique();
            playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
            handler.post(updateTemps);
        }

        // Pour la seekBar et la progession de la chanson
        dureeChanson.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) { // si on bouge la seekBar, alors on modifie l'état du exoplayer
                    singleton.getExoPlayer().seekTo(progress);
                    long currentPosition =  singleton.getExoPlayer().getCurrentPosition();
                    avancementChansonText.setText(formatDuration(currentPosition));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    // pour updater le temps d'une chanson
    public void updateSongDuration(Musique musiqueActuelle) {
        long duration = musiqueActuelle.getDuration() * 1000;
        String durationString = formatDuration(duration);
        tempsChansonText.setText(durationString);
    }

    // pour cast le format du temps depuis un long
    private String formatDuration(long duration) {
        long minutes = (duration / 1000) / 60;
        long seconds = (duration / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    // fonctionnel principal qui modifie le UI
    public void mettreAJourMusique(){
        Musique musiqueActuelle = gestionMusique.music.get(gestionMusique.enCours);
        nomChansonText.setText(musiqueActuelle.getTitle());
        nomArtisteText.setText(musiqueActuelle.getArtist());
        long duration = musiqueActuelle.getDuration() * 1000; // durée en millisecondes
        dureeChanson.setMax((int) duration);

        updateSongDuration(musiqueActuelle);
    }

    // Pour le boomerang qui modifie le volume
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private class ActivityCallBack implements ActivityResultCallback<ActivityResult> {
        @Override
        public void onActivityResult(ActivityResult o) {
            int volumeLevel = o.getData().getIntExtra("VOLUME_LEVEL", 0);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volumeLevel, 0);
        }
    }

    // Pour modifier le texte du temps de la chanson
    Runnable updateTemps = new Runnable() {
        @Override
        public void run() {
            long currentPosition = singleton.getExoPlayer().getCurrentPosition();
            avancementChansonText.setText(formatDuration(currentPosition));

            dureeChanson.setProgress((int) currentPosition);

            handler.postDelayed(this, 200);
        }
    };


    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // si on appuie sur play/pause
            if (v == playPauseButton) {
                if (singleton.getExoPlayer().isPlaying()) {
                    singleton.getExoPlayer().pause();
                    playPauseButton.setImageResource(android.R.drawable.ic_media_play);
                    handler.removeCallbacks(updateTemps); // arrete de update le timer
                } else {
                    if (!isPlaying) {
                        gestionMusique.jouerMusique(gestionMusique.enCours);
                        playerView.setPlayer(singleton.getExoPlayer());
                        isPlaying = true;
                    }
                    singleton.getExoPlayer().play();
                    playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
                    mettreAJourMusique();
                    handler.post(updateTemps); // update le timer
                }
                // si on change de chanson
            } else if (v == nouvelleChansonButton) {
                gestionMusique.prochaineChanson();
                singleton.getExoPlayer().play();
                playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
                handler.post(updateTemps);
                mettreAJourMusique();
                // si on recule de chanson
            } else if (v == ancienneChansonButton) {
                gestionMusique.ancienneChanson();
                singleton.getExoPlayer().play(); // recule de une chanson
                playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
                handler.post(updateTemps);
                mettreAJourMusique();
                // si on avance de 10 secondes
            } else if (v == avancerMusiqueChanson) {
                gestionMusique.avancerTemps(10000); // 10 secondes
                handler.post(updateTemps);
                mettreAJourMusique();
                // si on recule de 10 secondes
            } else if(v == retourMusiqueButton) {
                gestionMusique.reculerTemps(10000);
                handler.post(updateTemps);
                mettreAJourMusique();
                // pour avoir accès à toutes les playlist
            } else if(v == retourPlaylistButton) {
                Intent intent = new Intent(MainActivity.this, PlaylistActivity.class);
                startActivity(intent);
                // pour shuffle les chansons
            } else if(v == shuffleButton) {
                gestionMusique.shuffleMusique();
                mettreAJourMusique();
                if (singleton.getExoPlayer().getShuffleModeEnabled()) {
                    shuffleButton.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                } else {
                    shuffleButton.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                }
                // avoir accès au boomerang pour modifier le son
            } else if(v == parametersButton) {
                Intent intent = new Intent(MainActivity.this, ParametersActivity.class);
                launcher.launch(intent);
                // pour refaire jouer une chanson en boucle
            } else if(v == repeatButton) {
                gestionMusique.repeterChanson();
                mettreAJourMusique();
                if (singleton.getExoPlayer().getRepeatMode() == Player.REPEAT_MODE_ONE) {
                    repeatButton.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                } else {
                    repeatButton.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                }
            }
        }
    }

    // pour sérialiser une chanson lorsqu'on ferme l'application
    @Override
    protected void onStop() {
        super.onStop();
        try {
            singleton.setChansonEnCours(gestionMusique.getEnCours());
            long position = singleton.getExoPlayer().getCurrentPosition();
            singleton.setPositionChanson(position);
            singleton.serialiserListe();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
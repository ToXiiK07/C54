package com.example.tp1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;

import java.util.Random;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    ImageButton retourPlaylistButton, retourMusiqueButton, ancienneChansonButton, playPauseButton, nouvelleChansonButton, avancerMusiqueChanson, shuffleButton, repeatButton;
    TextView nomPlaylistText, nomChansonText, nomArtisteText, tempsChansonText, avancementChansonText;
    PlayerView playerView;
    SeekBar dureeChanson;
    ExoPlayer exoPlayer;
    Ecouteur ecouteur;
    Vector<Musique> vectorChanson;
    GestionMusique gestionMusique;
    Random random;
    Handler handler;

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
        vectorChanson = new Vector<>();
        random = new Random();
        handler = new Handler();

        exoPlayer = new ExoPlayer.Builder(getApplicationContext()).build();
        gestionMusique = new GestionMusique(exoPlayer);
        Modele modele = new Modele(MainActivity.this);

        // si on passe à travers de toutes les musiques, on recommence du début
        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackState == Player.STATE_ENDED) {
                    gestionMusique.prochaineChanson();
                    mettreAJourMusique();
                }
            }
        });

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



        // METTRE DANS LE MODÈLE

        modele.genererChangementValeur();


//        // la requête
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "https://api.jsonbin.io/v3/b/661ab8b1acd3cb34a837f284?meta=false";
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Gson gson = new GsonBuilder().create();
//
//                GestionMusique temp = gson.fromJson(response, GestionMusique.class);
//                gestionMusique.music = temp.music;
//            }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//        });
//        queue.add(stringRequest);

//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response)
//                {
//                    try {
//                       // Gson gson = new GsonBuilder().create();
//
//                        JSONArray tab = (JSONArray) response.get("music");
//                        JSONObject chanson;
//                        for (int i = 0; i < tab.length(); i++) {
//                            chanson = tab.getJSONObject(i);
//
//                            String nomChansonAPI = (String) chanson.get("title");
//                            String nomArtisteAPI = (String) chanson.get("artist");
//                            String imageAPI = (String) chanson.get("image");
//                            String nomAlbumAPI = (String) chanson.get("album");
//                            String genreAPI = (String) chanson.get("genre");
//                            int tempsAPI = (Integer) chanson.get("duration");
//                            String chansonAPI = (String) chanson.get("source");
//
//                            Musique musique = new Musique.Builder()
//                                    .setNomChanson(nomChansonAPI)
//                                    .setNomArtiste(nomArtisteAPI)
//                                    .setImage(imageAPI)
//                                    .setNomAlbum(nomAlbumAPI)
//                                    .setGenre(genreAPI)
//                                    .setTemps(tempsAPI)
//                                    .setChanson(chansonAPI)
//                                    .build();
//
//                            vectorChanson.add(musique);
//
//                        }
//                    } catch (JSONException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//
//            });
//
//        queue.add(jsonObjectRequest);

    }

    Runnable updateTemps = new Runnable() {
        @Override
        public void run() {
            long currentPosition = exoPlayer.getCurrentPosition();
            avancementChansonText.setText(formatDuration(currentPosition));

            // dureeChanson.setProgress((int) (currentPosition / 100));

            handler.postDelayed(this, 200);
        }
    };

    // pour la seekBar

//    public void genererChangementValeur()
//    {
//        Handler handler = new Handler();
//        handler.postDelayed(new Thread() {
//            int compteur = 0;
//            @Override
//            public void run() {
//                compteur++;
//                setValeur(compteur);
//                handler.postDelayed(this, 2000); // changement de valeur
//            }
//        }, 5000); // la méthode run s'exécutera après un délai de 5000 ms
//    }

    public void updateSongDuration(Musique musiqueActuelle) {
        long duration = musiqueActuelle.getDuration() * 1000;
        String durationString = formatDuration(duration);
        tempsChansonText.setText(durationString);
    }

    private String formatDuration(long duration) {
        long minutes = (duration / 1000) / 60;
        long seconds = (duration / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }


    public void mettreAJourMusique(){
        Musique musiqueActuelle = gestionMusique.music.get(gestionMusique.enCours);
        nomChansonText.setText(musiqueActuelle.getTitle());
        nomArtisteText.setText(musiqueActuelle.getArtist());
       // dureeChanson.setMax((int) exoPlayer.getDuration() * 1000);

        updateSongDuration(musiqueActuelle);
    }

    private class Ecouteur implements View.OnClickListener {
        private boolean isPlaying = false;
        private boolean isShuffled = false;

        @Override
        public void onClick(View v) {
            if (v == playPauseButton) {
                if (exoPlayer.isPlaying()) {
                    exoPlayer.pause();
                    playPauseButton.setImageResource(android.R.drawable.ic_media_play);
                    handler.removeCallbacks(updateTemps); // arrete de update le timer
                } else {
                    if (!isPlaying) {
                        // int r = random.nextInt(gestionMusique.music.size());
                        gestionMusique.jouerMusique(0);
                        playerView.setPlayer(exoPlayer);
                        isPlaying = true;
                    }
                    exoPlayer.play();
                    playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
                    mettreAJourMusique();
                    handler.post(updateTemps); // update le timer
                }
            } else if (v == nouvelleChansonButton) {
                gestionMusique.prochaineChanson();
                if(!exoPlayer.isPlaying()) {
                    exoPlayer.play();
                    playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
                    mettreAJourMusique();
                }
                // avance de une chanson
                mettreAJourMusique();
            } else if (v == ancienneChansonButton) {
                gestionMusique.ancienneChanson();
                exoPlayer.play(); // recule de une chanson
                mettreAJourMusique();
            } else if (v == avancerMusiqueChanson) {
                gestionMusique.avancerTemps(10000); // 10 secondes
                mettreAJourMusique();
            } else if(v == retourMusiqueButton) {
                gestionMusique.reculerTemps(10000);
                mettreAJourMusique();
            } else if(v == retourPlaylistButton) {
                Intent intent = new Intent(MainActivity.this, PlaylistActivity.class);
                startActivity(intent);
            } else if(v == shuffleButton) {
                gestionMusique.shuffleMusique();
                mettreAJourMusique();
                isShuffled = true;
            }
        }
    }
}
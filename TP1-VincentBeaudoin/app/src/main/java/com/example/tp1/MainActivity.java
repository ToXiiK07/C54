package com.example.tp1;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    ImageButton retourPlaylistButton, retourMusiqueButton, ancienneChansonButton, playPauseButton, nouvelleChansonButton, avancerMusiqueChanson;
    TextView nomPlaylistText, nomChansonText, nomArtisteText;
    PlayerView playerView;
    SeekBar dureeChanson;
    ExoPlayer exoPlayer;
    Ecouteur ecouteur;
    Vector<Musique> vectorChanson;
    MusiqueListe musiqueListe;

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

        exoPlayer = new ExoPlayer.Builder(getApplicationContext()).build();
        musiqueListe = new MusiqueListe(exoPlayer);

        // les boutons
        retourMusiqueButton = findViewById(R.id.retourMusiqueButton);
        retourPlaylistButton = findViewById(R.id.retourPlaylistButton);
        ancienneChansonButton = findViewById(R.id.ancienneChansonButton);
        playPauseButton = findViewById(R.id.playPauseButton);
        nouvelleChansonButton = findViewById(R.id.nouvelleChansonButton);
        avancerMusiqueChanson = findViewById(R.id.avancerMusiqueChanson);

        // les textViews
        nomPlaylistText = findViewById(R.id.titrePlaylist);
        nomChansonText = findViewById(R.id.nomChanson);
        nomArtisteText = findViewById(R.id.nomArtiste);

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

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.jsonbin.io/v3/b/661ab8b1acd3cb34a837f284?meta=false";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new GsonBuilder().create();

                MusiqueListe temp = gson.fromJson(response, MusiqueListe.class);
                musiqueListe.music = temp.music;
            }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
        });
        queue.add(stringRequest);

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



    public void mettreAJourMusique(){
        Musique musiqueActuelle = musiqueListe.music.get(musiqueListe.currentIndex);
        nomChansonText.setText(musiqueActuelle.getTitle());
        nomArtisteText.setText(musiqueActuelle.getArtist());
    }


    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == playPauseButton) {
                playerView.setPlayer(exoPlayer);
                mettreAJourMusique();

                musiqueListe.jouerMusique(5);

                if (exoPlayer.isPlaying()) {
                    exoPlayer.pause();
                    playPauseButton.setImageResource(android.R.drawable.ic_media_play);
                } else {
                    exoPlayer.play();
                    playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
                }
            } else if (v == nouvelleChansonButton) {
                musiqueListe.prochaineChanson();
                mettreAJourMusique();
            } else if (v == ancienneChansonButton) {
                musiqueListe.ancienneChanson();
                mettreAJourMusique();
            }
        }
    }

}
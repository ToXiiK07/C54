package com.example.tp1;

import android.content.Context;

import androidx.media3.exoplayer.ExoPlayer;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Singleton {

    private static Singleton instance;
    private List<Musique> listeMusique;
    private Context context;
    private RequestQueue queue;
    private Gson gson;
    private int chansonEnCours;
    private long positionChanson;
    private ExoPlayer exoPlayer;
    Hashtable<String, Object> musique;
    private boolean dejaLoader = false;
    private static final String URL = "https://api.jsonbin.io/v3/b/6723b430e41b4d34e44bfa92?meta=false";

    // constructeur du Singleton
    private Singleton(Context context) {
        this.context = context;
        listeMusique = new ArrayList<>();
        queue = Volley.newRequestQueue(context);
        gson = new GsonBuilder().create();

        if(exoPlayer == null) {
            exoPlayer =  new ExoPlayer.Builder(context).build(); // le seul Exoplayer de toute l'application
        }
    }

    public static Singleton getInstance(Context context) {
        if (instance == null) {
            instance = new Singleton(context);
        }
        return instance;
    }

    public ExoPlayer getExoPlayer() {
        return exoPlayer;
    }

    // Pour charger les musiques initalement
    // J'ai besoin du callback pour s'assurer que toutes les musiques ont loader avant de les jouer
    public void chargerMusique(MusiqueCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GestionMusique temp = gson.fromJson(response, GestionMusique.class);
                listeMusique = temp.getMusic(); // ajoute toutes les musiques à une List
                System.out.println("CONTENU DE LISTE MUSIQUE : " + listeMusique.size());
                if (callback != null) {
                    callback.onMusiqueCharger(listeMusique);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }

    public boolean isDejaLoader() {
        return dejaLoader;
    }

    public void setDejaLoader(boolean dejaLoader) {
        this.dejaLoader = dejaLoader;
    }

    public List<Musique> getListeMusique() {
        return listeMusique;
    }

    public void setChansonEnCours(int chansonEnCours) {
        this.chansonEnCours = chansonEnCours;
    }

    public void setPositionChanson(long positionChanson) {
        this.positionChanson = positionChanson;
    }

    // pour sérialiser une chanson
    public void serialiserListe() throws Exception {

        musique = new Hashtable<>();
        musique.put("index", chansonEnCours);
        musique.put("position", positionChanson);

        try (FileOutputStream fos = context.openFileOutput("musique_data.ser", Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(musique);
        }
    }

    // pour désérialiser une chanson
    public Hashtable<String, Object> deserialiserListe() throws Exception {
        try (FileInputStream fis = context.openFileInput("musique_data.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            musique = (Hashtable<String, Object>) ois.readObject();
        }
        return musique;
    }
}
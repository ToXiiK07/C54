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
    private static final String URL = "https://api.jsonbin.io/v3/b/661ab8b1acd3cb34a837f284?meta=false";

    private Singleton(Context context) {
        this.context = context.getApplicationContext();
        listeMusique = new ArrayList<>();
        queue = Volley.newRequestQueue(this.context);
        gson = new GsonBuilder().create();

        if(exoPlayer == null) {
            exoPlayer =  new ExoPlayer.Builder(context).build();
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

    public void chargerMusique(final MusiqueCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GestionMusique temp = gson.fromJson(response, GestionMusique.class);
                listeMusique = temp.getMusic();
                System.out.println("liste de musique : " + listeMusique.size());
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


    public List<Musique> getListeMusique() {
        return listeMusique;
    }

    public void setChansonEnCours(int chansonEnCours) {
        this.chansonEnCours = chansonEnCours;
    }

    public void setPositionChanson(long positionChanson) {
        this.positionChanson = positionChanson;
    }

    public int getChansonEnCours() {
        return chansonEnCours;
    }

    public void serialiserListe() throws Exception {
        try (FileOutputStream fos = context.openFileOutput("musique_data.ser", Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeInt(chansonEnCours);
            oos.writeLong(positionChanson);
        }
    }

    public void desirialiserListe() throws Exception {
        try (FileInputStream fis = context.openFileInput("musique_data.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            chansonEnCours = ois.readInt();
            positionChanson = ois.readLong();
            exoPlayer.seekTo(chansonEnCours, positionChanson);
            exoPlayer.prepare();
            exoPlayer.play();

        }
    }
}

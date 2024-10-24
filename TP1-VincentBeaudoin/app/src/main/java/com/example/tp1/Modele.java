package com.example.tp1;

import android.os.Handler;

import androidx.media3.exoplayer.ExoPlayer;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Modele {

    RequestQueue queue = Volley.newRequestQueue(this);
    String url = "https://api.jsonbin.io/v3/b/661ab8b1acd3cb34a837f284?meta=false";

    public Modele() {
        genererChangementValeur();
    }

    public void genererChangementValeur()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new GsonBuilder().create();

                // faire un Singleton pour passer à tout le monde

                GestionMusique temp = gson.fromJson(response, GestionMusique.class);
                gestionMusique.music = temp.music;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

}

package com.example.pratiqueexamen;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Observateur{
    ExoPlayer exoPlayer;
    PlayerView playerView;
    String URL = "https://api.jsonbin.io/v3/b/6723b430e41b4d34e44bfa92?meta=false";
    RequestQueue queue;
    Gson gson;
    Musique m;
    Modele modele;
    List list;
    View view;

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

        playerView = findViewById(R.id.player_view);
        view = findViewById(R.id.view);

        exoPlayer = new ExoPlayer.Builder(this).build();

        gson = new Gson();
        playerView.setPlayer(exoPlayer);

        modele = new Modele();
        modele.ajouterObservateur(this);

        queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                m = gson.fromJson(response, Musique.class);
                list = m.getMusic();
                modele.setMusiques(list);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }
    private static final long TEMPS_ANIMATION = 30000;
    private Handler handler = new Handler();

    @Override
    public void musiqueLoader() {
        List<Musique> musiques = modele.getMusiques();

        for (Musique musique : musiques) {
            MediaItem mediaItem = MediaItem.fromUri(musique.getSource());
            exoPlayer.addMediaItem(mediaItem);
        }

        exoPlayer.prepare();
        exoPlayer.play();

        verifierPosition();
    }

    private void verifierPosition() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                long currentPosition = exoPlayer.getCurrentPosition();
                if (currentPosition >= TEMPS_ANIMATION) {
                    lancerAnimation();
                } else {
                    verifierPosition();
                }

                // animation quand la chanson fini
//                long currentPosition = exoPlayer.getCurrentPosition();
//                long duration = exoPlayer.getDuration();
//
//                // Vérifiez si la chanson est terminée
//                if (duration > 0 && currentPosition >= duration) {
//                    lancerAnimation();
//                } else {
//                    // Continue la vérification périodique tant que la chanson n'est pas terminée
//                    verifierPosition();
//                }

            }
        }, 500);
    }

    private void lancerAnimation() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y,  500);
        objectAnimator.start();
    }

}
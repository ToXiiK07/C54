package com.example.tp1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.exoplayer.ExoPlayer;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class ChansonActivity extends AppCompatActivity {

    ListView listView;
    Button retourPlaylist;
    Ecouteur ec;
    String nomPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chanson);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.liste_chansons);
        retourPlaylist = findViewById(R.id.retourPlaylist);
        ec = new Ecouteur();

        String playlistId = getIntent().getStringExtra("playlist_id");
        nomPlaylist = getIntent().getStringExtra("playlist_name");


        afficherMusique(playlistId);

        retourPlaylist.setOnClickListener(ec);
    }

    private void afficherMusique(String playlistId) {
        Singleton singleton = Singleton.getInstance(this);
        singleton.chargerMusique(listeMusique -> {
            List<Hashtable<String, Object>> vector = new ArrayList<>();

            if(playlistId.equals("pl1")) {
                for (int i = 0; i < listeMusique.size(); i++) {

                    Musique musique = listeMusique.get(i);
                    Hashtable<String, Object> temp = new Hashtable<>();

                    temp.put("position", String.valueOf(i + 1));
                    temp.put("nom", musique.getTitle());
                    temp.put("artiste", musique.getArtist());
                    temp.put("image", musique.getImage());

                    vector.add(temp);
                }
            } else if(playlistId.equals("pl2")) {
                for (int i = 0; i < listeMusique.size(); i++) {

                    Musique musique = listeMusique.get(i);

                    if ("The Kyoto Connection".equals(musique.getArtist())) {
                        Hashtable<String, Object> temp = new Hashtable<>();
                        temp.put("position", String.valueOf(i + 1));
                        temp.put("nom", musique.getTitle());
                        temp.put("artiste", musique.getArtist());
                        temp.put("image", musique.getImage());

                        vector.add(temp);
                    }
                }
            } else if(playlistId.equals("pl3")) {
                for (int i = 0; i < listeMusique.size(); i++) {

                    Musique musique = listeMusique.get(i);

                    if ("Letter Box".equals(musique.getArtist()) || "Pixies".equals(musique.getArtist())) {
                        Hashtable<String, Object> temp = new Hashtable<>();
                        temp.put("position", String.valueOf(i + 1));
                        temp.put("nom", musique.getTitle());
                        temp.put("artiste", musique.getArtist());
                        temp.put("image", musique.getImage());

                        vector.add(temp);
                    }
                }
            } else if (playlistId.equals("pl4")) {
                Random r = new Random();
                HashSet<Musique> uniqueMusics = new HashSet<>();

                while (uniqueMusics.size() < 10 && uniqueMusics.size() < listeMusique.size()) {
                    Musique musique = listeMusique.get(r.nextInt(listeMusique.size()));
                    uniqueMusics.add(musique);
                }

                int i = 1;
                for (Musique musique : uniqueMusics) {
                    Hashtable<String, Object> temp = new Hashtable<>();
                    temp.put("position", String.valueOf(i));
                    temp.put("nom", musique.getTitle());
                    temp.put("artiste", musique.getArtist());
                    temp.put("image", musique.getImage());
                    vector.add(temp);
                    i++;
                }
            }

            SimpleAdapter adapter = new SimpleAdapter(this, vector, R.layout.chanson,
                    new String[]{"position", "nom", "artiste", "image"},
                    new int[]{R.id.textPosition, R.id.textNom, R.id.textArtiste, R.id.imageChanson}) {
                @Override
                public void setViewImage(ImageView v, String value) {
                    Glide.with(ChansonActivity.this).load(value).into(v);
                }
            };
            listView.setAdapter(adapter);

            listView.setOnItemClickListener((parent, view, position, id) -> {

                Hashtable<String, Object> chansonSelectionnee = vector.get(position);
                String nomChanson = (String) chansonSelectionnee.get("nom");

                Intent intent = new Intent(ChansonActivity.this, MainActivity.class);
                intent.putExtra("nom_chanson", nomChanson);
                intent.putExtra("playlist", nomPlaylist);
                startActivity(intent);
            });

        });
    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == retourPlaylist) {
                Intent intent = new Intent(ChansonActivity.this, PlaylistActivity.class);
                startActivity(intent);
            }
        }
    }
}

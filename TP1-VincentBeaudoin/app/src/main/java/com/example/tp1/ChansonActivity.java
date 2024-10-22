package com.example.tp1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChansonActivity extends AppCompatActivity {

    ListView listView;
    Button retourPlaylist;
    Ecouteur ec;
    GestionMusique gestionMusique;
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

    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(v == retourPlaylist){
                Intent intent = new Intent(ChansonActivity.this, PlaylistActivity.class);
                startActivity(intent);
            }

        }
    }
}
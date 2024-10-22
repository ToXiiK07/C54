package com.example.tp1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PlaylistActivity extends AppCompatActivity {

    LinearLayout pl1, pl2, pl3;
    Button retourMenu;
    Ecouteur ec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_playlist);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ec = new Ecouteur();

        retourMenu = findViewById(R.id.retourMenu);
        pl1 = findViewById(R.id.playlist_1);
        pl2 = findViewById(R.id.playlist_2);
        pl3 = findViewById(R.id.playlist_3);

        retourMenu.setOnClickListener(ec);
        pl1.setOnClickListener(ec);

    }
    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if(v == retourMenu){
                Intent intent = new Intent(PlaylistActivity.this, MainActivity.class);
                startActivity(intent);
            } else if(v == pl1) {
                Intent intent = new Intent(PlaylistActivity.this, ChansonActivity.class);
                startActivity(intent);

            }
        }
    }
}
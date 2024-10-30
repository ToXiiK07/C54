package com.example.tp1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PlaylistActivity extends AppCompatActivity {

    LinearLayout pl1, pl2, pl3, pl4;
    TextView pl1_text, pl2_text, pl3_text, pl4_text;
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
        pl1 = findViewById(R.id.playlist_1);
        pl2 = findViewById(R.id.playlist_2);
        pl3 = findViewById(R.id.playlist_3);
        pl4 = findViewById(R.id.playlist_4);

        pl1_text = findViewById(R.id.playlist_1_text);
        pl2_text = findViewById(R.id.playlist_2_text);
        pl3_text = findViewById(R.id.playlist_3_text);
        pl4_text = findViewById(R.id.playlist_4_text);

        pl1.setOnClickListener(ec);
        pl2.setOnClickListener(ec);
        pl3.setOnClickListener(ec);
        pl4.setOnClickListener(ec);

    }
    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == pl1) {
                Intent intent = new Intent(PlaylistActivity.this, ChansonActivity.class);
                intent.putExtra("playlist_id", "pl1");
                intent.putExtra("playlist_name", pl1_text.getText().toString());
                startActivity(intent);
            } else if (v == pl2) {
                Intent intent = new Intent(PlaylistActivity.this, ChansonActivity.class);
                intent.putExtra("playlist_id", "pl2");
                intent.putExtra("playlist_name", pl2_text.getText().toString());
                startActivity(intent);
            } else if (v == pl3) {
                Intent intent = new Intent(PlaylistActivity.this, ChansonActivity.class);
                intent.putExtra("playlist_id", "pl3");
                intent.putExtra("playlist_name", pl3_text.getText().toString());
                startActivity(intent);
            }  else if (v == pl4) {
                Intent intent = new Intent(PlaylistActivity.this, ChansonActivity.class);
                intent.putExtra("playlist_id", "pl4");
                intent.putExtra("playlist_name", pl4_text.getText().toString());
                startActivity(intent);
            }
        }

    }
}
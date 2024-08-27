package com.example.annexe1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Ajouter extends AppCompatActivity {

    Ecouteur ecouteur;
    Button ajouter, menu;
    TextView memo;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);

        ecouteur = new Ecouteur();

        ajouter = findViewById(R.id.ajouterTache);
        menu = findViewById(R.id.menu);
        memo = findViewById(R.id.tache);

        ajouter.setOnClickListener(ecouteur);
        menu.setOnClickListener(ecouteur);
    }

    private class Ecouteur implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(v == ajouter){
                String tacheAjoutee = memo.getText().toString();
                Intent intent = new Intent(Ajouter.this, Afficher.class);
                intent.putExtra("nouvelleTache", tacheAjoutee);
                //startActivity(intent);
            } else if (v == menu) {
                Intent intent = new Intent(Ajouter.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
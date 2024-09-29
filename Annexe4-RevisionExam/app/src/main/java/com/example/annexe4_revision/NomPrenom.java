package com.example.annexe4_revision;

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

public class NomPrenom extends AppCompatActivity {

    Ecouteur ec;
    Button confirmer;
    TextView nom;
    TextView prenom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nom_prenom);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ec = new Ecouteur();
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        confirmer = findViewById(R.id.confirmer);
        confirmer.setOnClickListener(ec);
    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(v == confirmer){
                Intent intent = new Intent(NomPrenom.this, MainActivity.class);

                String n = nom.getText().toString();
                String p = prenom.getText().toString();
                Utilisateur u = new Utilisateur(n, p);
                intent.putExtra("utilisateur", u);

                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}
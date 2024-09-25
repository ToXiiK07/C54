package com.example.annexe4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NomPrenom extends AppCompatActivity {

    EditText nom;
    EditText prenom;
    Button valider;

    Ecouteur ec;

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

        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        valider = findViewById(R.id.confirmer);
        ec = new Ecouteur();
        valider.setOnClickListener(ec);

    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(v == valider) {
                String p = prenom.getText().toString();
                String n = nom.getText().toString();

                Utilisateur utilisateur = new Utilisateur(p, n);
                Intent intent = new Intent();
                intent.putExtra("utilisateur", utilisateur);

                setResult(RESULT_OK, intent); // envoie les données
                finish();
            }
        }
    }
}
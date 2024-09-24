package com.example.annexe4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button connaitre;
    TextView nom;
    ActivityResultLauncher<Intent> lanceur;
    Ecouteur ec;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ec = new Ecouteur();
        connaitre = findViewById(R.id.connaitre);
        nom = findViewById(R.id.bonjour);
        connaitre.setOnClickListener(ec);

        lanceur = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Utilisateur utilisateur = (Utilisateur) data.getSerializableExtra("utilisateur");
                        if (utilisateur != null) {
                            nom.setText("Bonjour " + utilisateur.getPrenom() + " " + utilisateur.getNom() + "!");
                        }
                    }
                }
            }
        );
    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == connaitre) {
                Intent intent = new Intent(MainActivity.this, NomPrenom.class);
                lanceur.launch(intent);
            }
        }
    }
}
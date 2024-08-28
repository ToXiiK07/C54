package com.example.annexe1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    Ecouteur ec;
    Button ajouter, afficher, quitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ec = new Ecouteur();

        ajouter = findViewById(R.id.ajouter);
        afficher = findViewById(R.id.afficher);
        quitter = findViewById(R.id.quitter);

        ajouter.setOnClickListener(ec);
        afficher.setOnClickListener(ec);
        quitter.setOnClickListener(ec);

    }

    private class Ecouteur implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(v == quitter){
                finish();
            } else if(v == afficher) {
                Intent intent = new Intent(MainActivity.this, Afficher.class);
                startActivity(intent);
            } else if(v == ajouter) {
                Intent intent = new Intent(MainActivity.this, Ajouter.class);
                startActivity(intent);
            }
        }
    }
}
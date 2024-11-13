package com.example.annexe8b;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.Executor;
import java.util.function.IntConsumer;

public class MainActivity extends AppCompatActivity {

    Button gauche, titre, splash;
    Ecouteur ec;

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

        gauche = findViewById(R.id.gauche);
        titre = findViewById(R.id.titre);
        splash = findViewById(R.id.splash);

        ec = new Ecouteur();

        gauche.setOnClickListener(ec);
        titre.setOnClickListener(ec);
        splash.setOnClickListener(ec);

    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(v == gauche){
                Intent intent = new Intent(MainActivity.this, Gauche.class);
                startActivity(intent);
            } else if(v == titre){
                Intent intent = new Intent(MainActivity.this, Titre.class);
                startActivity(intent);
            } else if(v == splash){
                Intent intent = new Intent(MainActivity.this, Splash.class);
                startActivity(intent);
            }
        }
    }
}
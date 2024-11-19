package com.example.thread;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView auto1, auto2;
    TextView charge;
    Auto autoRouge, autoBleu;
    boolean courseTerminee = false;
    int c = 0;
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

        auto1 = findViewById(R.id.imageView);
        auto2 = findViewById(R.id.imageView2);

        charge = findViewById(R.id.textView);

        autoRouge = new Auto("rouge", 850);
        autoBleu = new Auto("bleu", 850);

        compteur();
        chargement();

    }

    Handler handler;
    public void compteur() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                c++;
                System.out.println(c);

                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    public void chargement() {
        handler.postDelayed(new Runnable() {
            int point = 0;
            int compteur = 0;
            @Override
            public void run() {
                String text = "Chargement";

                if (point == 0) {
                    text += "";
                } else if (point == 1) {
                    text += ".";
                } else if (point == 2) {
                    text += "..";
                } else if (point == 3) {
                    text += "...";
                } else if (point == 4) {
                    text += "....";
                }

                charge.setText(text);
                point++;
                if (compteur < 5) {
                    compteur++;
                    handler.postDelayed(this, 1000);
                } else {
                    charge.setText("La course commence !");
                    autoRouge.start();
                    autoBleu.start();
                }
            }
        }, 1000);
    }

    private class Auto extends Thread {
        private int posX;
        private String couleur;
        private boolean arret;
        private int limite;

        public Auto(String couleur, int limite){
            super(couleur);
            this.couleur = couleur;
            this.limite = limite;
            posX = 0;
            handler = new Handler();
            arret = false;
        }

        public void run() {
            while(!arret) {
                posX = posX + new Random().nextInt(30);
                if (posX >= limite) {
                    arret = true;
                    if(!courseTerminee){
                        courseTerminee = true;
                        handler.post(() -> charge.setText("Le gagnant est l'auto " + couleur + " !"));
                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(couleur.equals("rouge")){
                            auto1.setX(posX);
                        } else if (couleur.equals("bleu")) {
                            auto2.setX(posX);
                        }
                    }
                });

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
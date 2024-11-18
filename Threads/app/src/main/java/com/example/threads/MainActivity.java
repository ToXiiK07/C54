package com.example.threads;

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
    Auto jaune, vert;
    ImageView char1, char2;
    Handler h;


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

        char1 = findViewById(R.id.imageView3);
        char2 = findViewById(R.id.imageView4);

        jaune = new Auto("jaune");
        vert = new Auto("vert");

        jaune.run();
        vert.run();
    }

    public class Auto extends Thread{
        private int positionX;
        private String couleur;
        private boolean arret;

        public Auto(String couleur)
        {
            super(couleur); // nom du thread
            this.couleur = couleur;
            positionX =0;
            h = new Handler();

        }
        public void run() {
            while (!arret) {

               // System.out.println("  nom du Thread" + Thread.currentThread().getName());
                positionX = positionX + new Random().nextInt(80);
               // System.out.println(Thread.currentThread().getName() + " " + positionX);
                h.post(new Thread() {
                    public void run() {
                        if(couleur.equals("jaune")){
                            char1.setX(positionX);
                        }
                        else if(couleur.equals("vert")){
                            char2.setX(positionX);
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
package com.example.threads;

import android.widget.ImageView;

import java.util.Random;
import java.util.logging.Handler;

class Auto extends Thread {

    private int positionX;
    private String couleur;
    private boolean arret;
    private Handler handler;
    private ImageView runner;

    public Auto(String couleur, Handler handler, ImageView runner) {
        super(couleur);
        this.couleur = couleur;
        this.handler = handler;
        this.runner = runner;
        positionX = 0;
        arret = false;
    }

    public void run() {
        while (!arret) {
            System.out.println("nom du Thread: " + Thread.currentThread().getName());
            positionX = positionX + new Random().nextInt(80);
            System.out.println(Thread.currentThread().getName() + " position: " + positionX);

            // Vérifier si la voiture atteint la fin de l'écran (environ 1000 pixels)
            if (positionX >= 1000) {
                arret = true;
                positionX = 1000; // Arrêter à la fin de l'écran
            }

            // Utiliser le Handler pour mettre à jour la position de l'image sur le thread principal
            handler.post(new Runnable() {
                @Override
                public void run() {
                    runner.setTranslationX(positionX);
                }
            });

            // Pause pour simuler la vitesse du déplacement
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

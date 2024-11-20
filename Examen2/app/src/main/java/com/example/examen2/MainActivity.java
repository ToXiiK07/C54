package com.example.examen2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    ImageView mongolfiere;
    Button enregister;
    EditText reponse;
    ObjectAnimator monteY, droiteX, objectAnimator, objectAnimator2, objectAnimator3, objectAnimator4;
    AnimatorSet animatorSetPrincipal, animatorSet1, animatorSet2, animatorSet3;
    boolean q2, q3 = false;
    boolean q1 = true;
    Vector<AnimatorSet> listeAnimation;

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

        mongolfiere = findViewById(R.id.imageView);
        enregister = findViewById(R.id.button);
        reponse = findViewById(R.id.editTextText);

        //  ----- QUESTION 1 -----

//        monteY = ObjectAnimator.ofFloat(mongolfiere, View.TRANSLATION_Y, -400);
//        monteY.setDuration(2000);
//
//        droiteX = ObjectAnimator.ofFloat(mongolfiere, View.TRANSLATION_X, 300);
//        droiteX.setDuration(2000);

//        enregister.setOnClickListener(source -> {
//            monteY.start();
//            droiteX.start();
//        });

        //  ----- QUESTION 2 -----

        listeAnimation = new Vector<>();
        animatorSetPrincipal = new AnimatorSet();
        animatorSet1 = new AnimatorSet();
        animatorSet2 = new AnimatorSet();
        animatorSet3 = new AnimatorSet();

        monteY = ObjectAnimator.ofFloat(mongolfiere, View.TRANSLATION_Y, -300);
        monteY.setDuration(2000);
        droiteX = ObjectAnimator.ofFloat(mongolfiere, View.TRANSLATION_X, 300);
        droiteX.setDuration(2000);
        animatorSet1.playTogether(monteY, droiteX);

        listeAnimation.add(animatorSet1);

        objectAnimator = ObjectAnimator.ofFloat(mongolfiere, View.TRANSLATION_Y, -500);
        objectAnimator.setDuration(2000);
        objectAnimator2 = ObjectAnimator.ofFloat(mongolfiere, View.TRANSLATION_X, -100);
        objectAnimator2.setDuration(2000);
        animatorSet2.playTogether(objectAnimator, objectAnimator2);

        listeAnimation.add(animatorSet2);

        objectAnimator3 = ObjectAnimator.ofFloat(mongolfiere, View.TRANSLATION_X, -25);
        objectAnimator3.setDuration(2000);
        objectAnimator4 = ObjectAnimator.ofFloat(mongolfiere, View.TRANSLATION_Y, -1000);
        objectAnimator4.setDuration(2000);
        animatorSet3.playTogether(objectAnimator3, objectAnimator4);

        listeAnimation.add(animatorSet3);

        enregister.setOnClickListener(source -> {

            if(q1){
                listeAnimation.get(0).start();
                q2 = true;
                q1 = false;
            } else if(q2){
                listeAnimation.get(1).start();
                q2 = false;
                q3 = true;
            } else if(q3) {
                listeAnimation.get(2).start();
                q3 = false;
                q1 = true;
            }

            //animatorSetPrincipal.start();

        });
    }
}
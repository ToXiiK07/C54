package com.example.annexe8;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ObjectAnimator objectAnimator, objectAnimator2, objectAnimator3, objectAnimator4;
    AnimatorSet animationSet = new AnimatorSet();
    View object;
    Button button;


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

        object = findViewById(R.id.view);
        button = findViewById(R.id.button);


        // Pour faire un cercle !!

//        Path p = new Path();
//        p.moveTo(50, 200);
//        p.lineTo(900, 200);
//        p.lineTo(900, 650);
//        p.arcTo(400, 600 ,900, 1000, 0, 359, false);
//
//        objectAnimator4 = ObjectAnimator.ofFloat(object, "x", "y", p);
//
//        objectAnimator4.setDuration(2500);
//        objectAnimator4.setRepeatCount(ValueAnimator.INFINITE);
//        objectAnimator4.setRepeatMode(ValueAnimator.REVERSE);
//        button.setOnClickListener(source-> objectAnimator4.start());

        objectAnimator = ObjectAnimator.ofFloat(object, View.TRANSLATION_X, 500);
        objectAnimator2 = ObjectAnimator.ofFloat(object, View.SCALE_Y, 15);
        objectAnimator3 = ObjectAnimator.ofArgb(object, "backgroundColor", Color.RED, Color.YELLOW);

        animationSet.playTogether(objectAnimator, objectAnimator2, objectAnimator3);
        animationSet.setDuration(5000); // valeur par défaut 300ms
        animationSet.setInterpolator(new BounceInterpolator()); // rebondi

        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);


        button.setOnClickListener(source-> animationSet.start());

    }

}
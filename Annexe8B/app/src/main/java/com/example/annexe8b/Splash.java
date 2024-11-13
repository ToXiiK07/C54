package com.example.annexe8b;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Splash extends AppCompatActivity {

    AnimatorSet firstAnimatorSet, secondAnimatorSet;
    ObjectAnimator descend, scale_x, scale_y;
    View soleil;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firstAnimatorSet = new AnimatorSet();
        secondAnimatorSet = new AnimatorSet();

        soleil = findViewById(R.id.soleil);
        start = findViewById(R.id.button2);

        descend = ObjectAnimator.ofFloat(soleil, View.TRANSLATION_Y, 1000);
        descend.setDuration(2000);

        scale_x = ObjectAnimator.ofFloat(soleil, View.SCALE_X, 15);
        scale_y = ObjectAnimator.ofFloat(soleil, View.SCALE_Y, 15);

        secondAnimatorSet.playTogether(scale_x, scale_y);
        secondAnimatorSet.setDuration(2000);
        firstAnimatorSet.playSequentially(descend, secondAnimatorSet);

        start.setOnClickListener(source-> {
            firstAnimatorSet.start();
        });
    }
}
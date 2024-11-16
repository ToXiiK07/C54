package com.example.animation;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.graphics.Color;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    AnimatorSet animatorSet;
    View view;
    Button start;
    boolean isAnimating = false;

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

        view = findViewById(R.id.view);
        start = findViewById(R.id.button);

        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, View.ROTATION, 0f, 360f);
        rotation.setDuration(2000);
        rotation.setRepeatCount(ObjectAnimator.INFINITE);

        ObjectAnimator colorChange = ObjectAnimator.ofArgb(view, "backgroundColor", Color.RED, Color.BLUE);
        colorChange.setDuration(2000);
        colorChange.setEvaluator(new ArgbEvaluator());
        colorChange.setRepeatCount(ObjectAnimator.INFINITE);
        colorChange.setRepeatMode(ObjectAnimator.REVERSE);

        ObjectAnimator translation = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0f, 500f);
        translation.setDuration(2000);
        translation.setRepeatCount(ObjectAnimator.INFINITE);
        translation.setRepeatMode(ObjectAnimator.REVERSE);

        animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotation, colorChange, translation);

        start.setOnClickListener(source -> {
            if (isAnimating) {
                animatorSet.pause();
                isAnimating = false;
            } else {
                animatorSet.start();
                isAnimating = true;
            }
        });
    }
}
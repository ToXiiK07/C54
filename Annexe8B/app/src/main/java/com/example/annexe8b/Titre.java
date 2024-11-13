package com.example.annexe8b;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Titre extends AppCompatActivity {

    Button start;
    TextView textView;
    ObjectAnimator scale_x, scale_y;
    boolean isPlaying;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_titre);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textView = findViewById(R.id.textView);
        textView.setAlpha(0f);
        start = findViewById(R.id.start);
        isPlaying = false;
        AnimatorSet animationSet = new AnimatorSet();

        scale_x = ObjectAnimator.ofFloat(textView, View.SCALE_X, 5);
        scale_y = ObjectAnimator.ofFloat(textView, View.SCALE_Y, 5);

        scale_y.setRepeatCount(ValueAnimator.INFINITE);
        scale_y.setRepeatMode(ValueAnimator.REVERSE);

        scale_x.setRepeatCount(ValueAnimator.INFINITE);
        scale_x.setRepeatMode(ValueAnimator.REVERSE);

        animationSet.playTogether(scale_x, scale_y);

        start.setOnClickListener(source-> {
            if(!isPlaying){
                textView.setAlpha(0.9f);
                animationSet.start();
                isPlaying = true;
            } else {
                textView.setAlpha(0f);
                animationSet.end();
                isPlaying = false;
            }
        });
    }
}
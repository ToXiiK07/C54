package com.example.animationpath;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    View view;
    ObjectAnimator objectAnimator, objectAnimator2, objectAnimator3, objectAnimator4;

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
        AnimatorSet animatorSet = new AnimatorSet();

        Path cerclePath = new Path();
        cerclePath.moveTo(50, 200);
        cerclePath.lineTo(900, 200);
        cerclePath.lineTo(900, 650);
        cerclePath.arcTo(400, 600 ,900, 1000, 0, 359, false);

        Path largeSquarePath = new Path();
        largeSquarePath.moveTo(100, 100); // Point de départ en haut à gauche
        largeSquarePath.lineTo(500, 100); // Ligne horizontale vers la droite (100 + 400)
        largeSquarePath.lineTo(500, 500); // Ligne verticale vers le bas (100 + 400)
        largeSquarePath.lineTo(100, 500); // Ligne horizontale vers la gauche
        largeSquarePath.lineTo(100, 100); // Retour au point de départ pour fermer le carré

        Path trianglePath = new Path();
        trianglePath.moveTo(300, 100);  // Point en haut
        trianglePath.lineTo(100, 500);  // Bas à gauche
        trianglePath.lineTo(500, 500);  // Bas à droite
        trianglePath.lineTo(300, 100);  // Retour au point de départ pour fermer le triangle

        Path zigzagPath = new Path();
        zigzagPath.moveTo(100, 200);  // Point de départ
        zigzagPath.lineTo(200, 100);  // Zig vers le haut à droite
        zigzagPath.lineTo(300, 200);  // Zag vers le bas à droite
        zigzagPath.lineTo(400, 100);  // Zig vers le haut à droite
        zigzagPath.lineTo(500, 200);  // Zag vers le bas à droite
        zigzagPath.lineTo(600, 100);  // Zig vers le haut à droite
        zigzagPath.lineTo(700, 200);  // Zag vers le bas à droite

        Path path = new Path();
        path.moveTo(0, 0);
        path.quadTo(300, 500, 600, 0);

        objectAnimator = ObjectAnimator.ofFloat(view, "x", "y", path);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);

        objectAnimator2 = ObjectAnimator.ofObject(view, "backgroundColor", new ArgbEvaluator(), Color.RED, Color.BLUE);
        objectAnimator2.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator2.setRepeatMode(ValueAnimator.REVERSE);

        animatorSet.playTogether(objectAnimator, objectAnimator2);
        animatorSet.setDuration(5000);
        animatorSet.start();
    }
}
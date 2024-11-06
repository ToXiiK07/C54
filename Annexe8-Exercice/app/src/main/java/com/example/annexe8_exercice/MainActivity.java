package com.example.annexe8_exercice;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ObjectAnimator objectAnimator;
    LinearLayout linearLayout;
    boolean ouvert = false;
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

        linearLayout = findViewById(R.id.linear);

        objectAnimator = ObjectAnimator.ofFloat(linearLayout, View.TRANSLATION_Y, -100);



        linearLayout.setOnClickListener(source-> {

            if(!ouvert){
                objectAnimator.start();
                ouvert = true;
            } else {
                objectAnimator.reverse();
                ouvert = false;
            }
        });
    }
}
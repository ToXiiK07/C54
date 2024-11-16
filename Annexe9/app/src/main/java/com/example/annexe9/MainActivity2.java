package com.example.annexe9;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    List<Voiture> voitureList;
    Double prix;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        InputStream is = getResources().openRawResource(R.raw.voitures);
        InputStreamReader inputStreamReader = new InputStreamReader(is);

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder stringBuilder = new StringBuilder();

        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }

            Gson gson = new Gson();
            Voiture voiture = gson.fromJson(stringBuilder.toString(), Voiture.class);
            voitureList = voiture.getVoitures();

            prix = (double) 0;
            for (int i = 0; i < voitureList.size(); i++) {
                Voiture v = voitureList.get(i);
                prix += v.getPrix();
            }

            System.out.println("prix total : " + prix);
            double moyenne = prix / voitureList.size();
            System.out.println(moyenne);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
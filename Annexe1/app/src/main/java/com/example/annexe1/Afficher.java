package com.example.annexe1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Vector;

public class Afficher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher);

        Spinner spinner = findViewById(R.id.spinner);
        Vector<String> v = new Vector<>();

        Intent intent = getIntent();
        String message = intent.getStringExtra("nouvelleTache");
        if (message != null) {
            v.add(message);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, v);
        spinner.setAdapter(arrayAdapter);
    }
}

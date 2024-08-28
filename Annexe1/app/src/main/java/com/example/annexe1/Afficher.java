package com.example.annexe1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Vector;

public class Afficher extends AppCompatActivity {
    Vector<String> v = new Vector<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher);

        ListView listView = findViewById(R.id.liste_memo);

        Intent intent = getIntent();
        String message = intent.getStringExtra("nouvelleTache");
        if (message != null) {
            v.add(message);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, v);
        listView.setAdapter(arrayAdapter);
    }
}

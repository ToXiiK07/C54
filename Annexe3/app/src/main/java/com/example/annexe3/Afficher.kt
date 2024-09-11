package com.example.annexe3

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.annexe1.R
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Vector

class Afficher : AppCompatActivity() {

    lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_afficher)

        listView = findViewById(R.id.liste_memo)
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, Vector<String>())
        listView.setAdapter(arrayAdapter)
    }





}
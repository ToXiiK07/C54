package com.example.annexe1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
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
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lesMemos())
        listView.setAdapter(arrayAdapter)
    }
    fun lesMemos(): Vector<String>
    {
        val v = Vector<String>()
        val fis : FileInputStream = openFileInput("fichier.txt")// append : écrit à la fin du fichier plutot qu'au début
        val isr = InputStreamReader(fis) // traduit en caractères
        val br = BufferedReader(isr) // plus rapide
        br.forEachLine { s -> v.add(s) }
        br.close()
        return v
    }
}

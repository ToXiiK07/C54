package com.example.annexe1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedWriter
import java.io.FileOutputStream
import java.io.OutputStreamWriter

class Ajouter : AppCompatActivity() {
    lateinit var ajouter: Button
    lateinit var menu: Button
    lateinit var memo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_ajouter)

        val ecouteur = Ecouteur()

        ajouter = findViewById(R.id.ajouterTache)
        menu = findViewById(R.id.menu)
        memo = findViewById(R.id.tache)

        ajouter.setOnClickListener(ecouteur)
        menu.setOnClickListener(ecouteur)
    }

    inner class Ecouteur : View.OnClickListener {
        override fun onClick(v: View) {
            if (v === ajouter) {
                var tacheAjoutee = memo.text.toString()
                val fos : FileOutputStream = openFileOutput("fichier.txt", MODE_APPEND) // append : écrit à la fin du fichier plutot qu'au début
                val osw = OutputStreamWriter(fos) // traduit en caractères
                val bw = BufferedWriter(osw) // plus rapide
                bw.write(tacheAjoutee)
                bw.newLine() // pour changer de ligne
                bw.close() // fermer le buffer
                finish() // pour revenir au menu principal
            } else if (v === menu) {
                val intent = Intent(this@Ajouter, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
package com.example.annexe1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var ajouter: Button
    lateinit var afficher: Button
    lateinit var quitter: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val ec = Ecouteur() // déclaration du type faculatif et pas de new

        ajouter = findViewById(R.id.ajouter)
        afficher = findViewById(R.id.afficher)
        quitter = findViewById(R.id.quitter)

        ajouter.setOnClickListener(ec)
        afficher.setOnClickListener(ec)
        quitter.setOnClickListener(ec)
    }

    inner class Ecouteur : View.OnClickListener { // inner = class interne
        override fun onClick(v: View) {
            if (v === quitter) {
                finish()
            } else if (v === afficher) {
                val intent = Intent(this@MainActivity, Afficher::class.java)
                startActivity(intent)
            } else if (v === ajouter) {
                val intent = Intent(this@MainActivity, Ajouter::class.java)
                startActivity(intent)
            }
        }
    }
}
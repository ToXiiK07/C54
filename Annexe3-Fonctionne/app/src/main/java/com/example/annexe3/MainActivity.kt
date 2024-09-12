package com.example.annexe3

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var ajouter: Button
    lateinit var afficher: Button
    lateinit var quitter: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val ec = Ecouteur()

        ajouter = findViewById(R.id.ajouter)
        afficher = findViewById(R.id.afficher)
        quitter = findViewById(R.id.quitter)

        ajouter.setOnClickListener(ec)
        afficher.setOnClickListener(ec)
        quitter.setOnClickListener(ec)

        try {
            val ref = Singleton.getInstance(applicationContext);
            val temp = ref.deserialiserListe();
            ref.listeMemo = temp;
        } catch (e: Exception) {
            e.printStackTrace();
        }

    }


    inner class Ecouteur : View.OnClickListener { // inner = class interne
        override fun onClick(v: View) {
            if (v === quitter) {
                finish()

            } else if (v === afficher) {
                val intent = Intent(this@MainActivity, AfficherActivity::class.java)
                startActivity(intent)
            } else if (v === ajouter) {
                val intent = Intent(this@MainActivity, AjouterActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
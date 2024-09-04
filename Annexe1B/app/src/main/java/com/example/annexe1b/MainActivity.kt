package com.example.annexe1b

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {

    val ec = Ecouteur()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nbLigne()
        nbCaractere()
        nbDeC()
        ajouterNom()
    }

    fun nbLigne(){
        var nbLignesFichier = 0

        val fis : FileInputStream = openFileInput("Fichier1.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)
        br.forEachLine { nbLignesFichier++ }
        br.close()
        println(nbLignesFichier)
    }

    fun nbCaractere() {
        var nbCaract = 0

        val fis : FileInputStream = openFileInput("Fichier1.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)

        br.forEachLine { nbCaract += it.length }

        br.close()
        println(nbCaract)
    }

    fun nbDeC() {
        var c = 0

        val fis : FileInputStream = openFileInput("Fichier1.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)

        br.forEachLine {
            it.forEach {
                if(it == 'c'){
                    c++
                }
            }
        }

        br.close()
        println(c)
    }

    fun ajouterNom() {
        val fos : FileOutputStream = openFileOutput("Fichier1.txt", MODE_APPEND)
        val osw = OutputStreamWriter(fos)
        val bw = BufferedWriter(osw)
        bw.write("Vincent Beaudoin")
        bw.newLine()
        bw.close()
    }


    inner class Ecouteur : View.OnClickListener { // inner = class interne
        override fun onClick(v: View) {


        }
    }
}
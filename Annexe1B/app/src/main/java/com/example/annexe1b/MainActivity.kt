package com.example.annexe1b

import android.os.Bundle
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
import java.util.Scanner
import java.util.Vector

class MainActivity : AppCompatActivity() {

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
        nbMot()

        var planet = Planet()
        planet.nbPlanetes()
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
                if(it == 'c' || it == 'C'){
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

    // Le délimiteur par défaut est un caractère blanc (espace, tabulation, saut de ligne)
    fun nbMot() { // avec scanner
        var compteur = 0
        val fis : FileInputStream = openFileInput("Fichier1.txt")
        val sc = Scanner(fis)
        while (sc.hasNext()) {
            println(sc.next())
            compteur++
        }
        sc.close()
    }


    inner class Planet {
        var nomPlanet = ""
        var nbLunes = 0

        fun nbPlanetes() {
            val vectorPlanetes = Vector<String>();
            val fis : FileInputStream = openFileInput("Fichier2.txt")
            val sc = Scanner(fis)
            while (sc.hasNext()) {
                val planete = Planet()
                planete.nomPlanet = sc.next()
                planete.nbLunes = sc.nextInt()
                vectorPlanetes.add(planete.toString())
               // println(sc.next())
            }
        }
    }


    inner class Planete(var nom:String, var nbsat : Int){ // constructeur primaire !!


    }

}
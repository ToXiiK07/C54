package com.example.examen1

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Scanner
import java.util.Vector

class MainActivity : AppCompatActivity() {


    lateinit var utilisation: TextView
    lateinit var compteur : TextView
    lateinit var instance : Singleton

    var compteurExiste = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

         listeLangage()

        instance = Singleton.getInstance(applicationContext)
        utilisation = findViewById(R.id.utilisation)
        compteur = findViewById(R.id.compteur)

        compteur.text = "Langage pas créé en 2012 : " + compteurExiste.toString()

        var date = instance.date
        // var date_heure = date.format(DateTimeFormatter.ISO_LOCAL_TIME)

        if(instance == null) {
            utilisation.text = "1ère utilisation"
        } else {
            utilisation.text = "Dernière utilisation : " + date
        }

        try {
            instance.deserialiserListe()
            utilisation.text = "Dernière utilisation : " + date
        } catch (e: Exception) {
            e.printStackTrace();
        }
    }

    override fun onStop() {
        super.onStop()
        instance.serialiserListe()
    }


    fun listeLangage(): Vector<String> {
        var nomLangage = " "
        var classement2024 = 0
        var classement2019 = 0
        var classement2012 = 0


        val v = Vector<String>()
        val fis: FileInputStream = openFileInput("langage.txt")
        val sc = Scanner(fis)
        sc.delimiter()
        while (sc.hasNext()) {
            sc.useDelimiter(",")
            val langage = Langage(nomLangage, classement2024, classement2019, classement2012)
            langage.nomLangage = sc.next()
            langage.classement2024 = sc.nextInt()
            sc.useDelimiter(",")
            langage.classement2019 = sc.nextInt()
            sc.useDelimiter(",")
            langage.classement2012 = sc.nextInt()

            v.add(langage.toString())
            if(langage.classement2012 == 99){
                compteurExiste++
            }
        }

        return v
    }


    inner class Langage(var nomLangage:String, var classement2024 : Int, var classement2019 : Int, var classement2012 : Int){

    }

}
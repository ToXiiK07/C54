package com.example.annexe3

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate

class AjouterActivity : AppCompatActivity() {

    lateinit var ajouter: Button
    lateinit var menu: Button
    lateinit var date: Button
    lateinit var memo: TextView
    lateinit var dateVue: TextView
    var dateChoisie: LocalDate = LocalDate.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ajouter)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val ecouteur = Ecouteur()

        ajouter = findViewById(R.id.ajouterTache)
        menu = findViewById(R.id.menu)
        memo = findViewById(R.id.tache)
        date = findViewById(R.id.date)
        dateVue = findViewById(R.id.dateVue)

        ajouter.setOnClickListener(ecouteur)
        menu.setOnClickListener(ecouteur)
        date.setOnClickListener(ecouteur)

    }

    override fun onStop() {
        super.onStop()
        // on veut sérialiser la liste dans un fichier pour
        // la récupérer quand on va revenir
        try {
            Singleton.getInstance(applicationContext).serialiserListe()
        } catch(e:Exception) {
            e.printStackTrace()
        }


    }

    inner class Ecouteur : View.OnClickListener {
        override fun onClick(v: View) {
            if (v === menu) {
                val intent = Intent(this@AjouterActivity, MainActivity::class.java)
                startActivity(intent)
            } else if (v === date) {
                var datePicker = DatePickerDialog(this@AjouterActivity)
                datePicker.show()
                datePicker.setOnDateSetListener { view, year, month, dayOfMonth ->
                    dateVue.text = LocalDate.of(year, month, dayOfMonth).toString()
                    dateChoisie = LocalDate.of(year, month+1, dayOfMonth)
                }
            }else if (v === ajouter) {
                var tacheAjoutee = memo.text.toString()
                Singleton.getInstance(applicationContext).ajouterMemo(Memo(tacheAjoutee, dateChoisie))

                finish() // pour revenir au menu principal
            }

        }
    }
}
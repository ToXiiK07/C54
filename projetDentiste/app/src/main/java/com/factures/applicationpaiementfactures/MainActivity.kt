package com.factures.applicationpaiementfactures

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText

import android.widget.LinearLayout

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    lateinit var dent1: LinearLayout
    lateinit var dent2: LinearLayout
    lateinit var numDent1: EditText
    lateinit var numDent2: EditText
    lateinit var noteDent1: EditText
    lateinit var noteDent2: EditText
    lateinit var canalDent1: CheckBox
    lateinit var canalDent2: CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val singleton = Singleton.getInstance(applicationContext)

        dent1 = findViewById(R.id.dent1)
        dent2 = findViewById(R.id.dent2)
        numDent1 = findViewById(R.id.numDent1)
        numDent2 = findViewById(R.id.numDent2)
        noteDent1 = findViewById(R.id.noteDent1)
        noteDent2 = findViewById(R.id.noteDent2)
        canalDent1 = findViewById(R.id.canalDent1)
        canalDent2 = findViewById(R.id.canalDent2)


        try {
            val temp = singleton.deserialiserListe()
            singleton.listeDent = temp
            noteDent1.setText(singleton.listeDent[0].memo)
            numDent1.setText(singleton.listeDent[0].numDent)
            canalDent1.isChecked = singleton.listeDent[0].check

            noteDent2.setText(singleton.listeDent[1].memo)
            numDent2.setText(singleton.listeDent[1].numDent)
            canalDent2.isChecked = singleton.listeDent[1].check
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onStop() {
        super.onStop()

        val memo = noteDent1.text.toString()
        val numDent = numDent1.text.toString()
        val canalDent = canalDent1.isChecked()
        Singleton.getInstance(applicationContext).ajouterDent(Dent(memo, numDent, canalDent))

        val memo2 = noteDent2.text.toString()
        val numDent2 = numDent2.text.toString()
        val canalDent2 = canalDent2.isChecked()
        Singleton.getInstance(applicationContext).ajouterDent(Dent(memo2, numDent2, canalDent2))

        try {
            Singleton.getInstance(applicationContext).serialiserListe()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
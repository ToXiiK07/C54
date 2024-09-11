package com.example.annexe3

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Vector

class AfficherActivity : AppCompatActivity() {

    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_afficher)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        listView = findViewById(R.id.liste_memo)
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, convertir())
        listView.setAdapter(arrayAdapter)

    }

    fun convertir(): Vector<String> {
        var listeTexteMemos = Vector<String>()
        var listeMemo = Singleton.getInstance().listeMemo

        for (memo in listeMemo) {
            listeTexteMemos.add(memo.memo)
        }

        return listeTexteMemos
    }
}
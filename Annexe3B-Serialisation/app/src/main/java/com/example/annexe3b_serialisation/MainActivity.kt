package com.example.annexe3b_serialisation

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var sonnerie: SeekBar
    lateinit var media: SeekBar
    lateinit var notif: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val singleton = Singleton.getInstance(applicationContext)

        sonnerie = findViewById(R.id.sonnerie)
        media = findViewById(R.id.media)
        notif = findViewById(R.id.notif)

        sonnerie.setProgress(singleton.getSonnerieProgress())
        media.setProgress(singleton.getMediaProgress())
        notif.setProgress(singleton.getNotifProgress())

        sonnerie.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                singleton.setSeekBar( sonnerie.getProgress(),
                    media.getProgress(), notif.getProgress() )

                singleton.ajouterSon("sonnerie", sonnerie.getProgress())
                singleton.ajouterSon("media", media.getProgress())
                singleton.ajouterSon("notif", notif.getProgress())
            }
        })

        try {
            singleton.deserialiserListe()
            sonnerie.progress = singleton.getSonnerieProgress()
            media.progress = singleton.getMediaProgress()
            notif.progress = singleton.getNotifProgress()
        } catch (e: Exception) {
            e.printStackTrace();
        }

    }

    override fun onStop() {
        super.onStop()
        try {
            Singleton.getInstance(applicationContext).serialiserListe()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
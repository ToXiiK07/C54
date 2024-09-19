package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        Log.i("cycle", "onCreate")
    }

    override fun onStart() {
        Log.i("cycle", "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.i("cycle", "onResume")
        super.onResume()
    }

    override fun onRestart() {
        Log.i("cycle", "onRestart")
        super.onRestart()
    }

    override fun onPause() {
        Log.i("cycle", "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.i("cycle", "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.i("cycle", "onDestroy")
        super.onDestroy()
    }

}
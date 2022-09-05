package com.example.psamiproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.content.Intent


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val Tekst = findViewById(R.id.tekst) as TextView
        val Zaloguj = findViewById<Button>(R.id.button_zaloguj)
        Zaloguj.setOnClickListener {
            Tekst.setText("Zaloguj")
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
        }
    }

}
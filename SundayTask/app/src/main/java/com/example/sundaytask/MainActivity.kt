package com.example.sundaytask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text = findViewById<TextView>(R.id.textView)
        val button = findViewById<Button>(R.id.buttonChange)
        val buttonSecond = findViewById<Button>(R.id.secondActivitybtn)

        button.setOnClickListener {
            text.text = "Good Bye"
        }

        buttonSecond.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }
}

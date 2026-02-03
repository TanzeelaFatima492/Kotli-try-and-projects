package com.example.sundaytask

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val buttonToast = findViewById<Button>(R.id.btnToast)
        buttonToast.setOnClickListener {
            Toast.makeText(this, "Hello from Second Activity!", Toast.LENGTH_SHORT).show()
        }
    }
}

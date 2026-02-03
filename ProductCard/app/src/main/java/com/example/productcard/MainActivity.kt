package com.example.productcard

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var fab: FloatingActionButton
    private lateinit var donutImage: ImageView
    private lateinit var iceImage: ImageView
    private lateinit var cakeImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.aactivity_main)

        fab = findViewById(R.id.fab)
        donutImage = findViewById(R.id.imageDonut)
        iceImage = findViewById(R.id.imageIce)
        cakeImage = findViewById(R.id.imageCake)

        donutImage.setOnClickListener {
            Toast.makeText(this, "Donut added to cart", Toast.LENGTH_SHORT).show()
        }

        iceImage.setOnClickListener {
            Toast.makeText(this, "Ice Cream added to cart", Toast.LENGTH_SHORT).show()
        }

        cakeImage.setOnClickListener {
            Toast.makeText(this, "FroYo added to cart", Toast.LENGTH_SHORT).show()
        }

        fab.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }
}

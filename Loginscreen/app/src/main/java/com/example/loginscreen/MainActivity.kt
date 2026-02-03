package com.example.loginscreen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var usernameInput :EditText
    lateinit var passwordInput :EditText
    lateinit var loginBtn :Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        usernameInput = findViewById(R.id.username_input)
        passwordInput = findViewById(R.id.Password_input)
        LoginBtn = findViewById(R.id.login_btn)

        LoginBtn.setOnClickListener{

            val username=username_input.text.toString()
            val password=Password_input.text.toString()
            Log.i("Test Credentials","Username: $username and Password :$password")


        }

    }
    }
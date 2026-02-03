package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener{

    // new variable
    lateinit var Addbtn:Button
    lateinit var Subbtn:Button
    lateinit var Multibtn:Button
    lateinit var Divisionbtn:Button
    lateinit var eta:EditText
    lateinit var etb:EditText
    lateinit var result:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        //Assign the ids
            Addbtn=findViewById(R.id.e_add)
            Subbtn=findViewById(R.id.e_sub)
            Multibtn=findViewById(R.id.e_multi)
            Divisionbtn=findViewById(R.id.e_divi)
            eta=findViewById(R.id.et_a)
            etb=findViewById(R.id.et_b)
            result=findViewById(R.id.Result)


        // Assign a click listener so they work

        Addbtn.setOnClickListener(this)
        Subbtn.setOnClickListener(this)
        Multibtn.setOnClickListener(this)
        Divisionbtn.setOnClickListener(this)
        }

    override fun onClick(v: View?) {

        var a=eta.text.toString().toDouble()
        var b=etb.text.toString().toDouble()
        var Res=0.0

        // case
        if (v != null) {
            when(v.id) {
                R.id.e_add -> {
                    Res = a + b
                }

                R.id.e_sub -> {
                    Res = a - b
                }

                R.id.e_multi -> {
                    Res = a * b
                }

                R.id.e_divi -> {
                    Res = a / b
                }
            }
        }

        result.text="Result is $Res"
    }
}
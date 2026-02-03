package com.example.firstandroidapp

import android.os.Bundle
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.firstandroidapp.ui.theme.FirstAndroidAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FirstAndroidAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Tanzeela Fatima")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Surface(color = Color.Green) {
        Text(
            text = "Hi, my name is $name!",
            modifier = modifier.padding(80.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FirstAndroidAppTheme {
        Greeting("Tanzeela")
    }
}

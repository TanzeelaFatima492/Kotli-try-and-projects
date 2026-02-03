package com.example.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BusinessCardTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingText(message = "Happy Birthday!", from = "From Tanzeela")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BirthdayCardPreview() {
    BusinessCardTheme {
        GreetingText(message = "Happy Birthday!", from = "From Tanzeela")
    }
}

@Composable
fun GreetingText(message: String, from: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp)
    )
    {
        Text(
            text = message,
            fontSize = 50.sp,
            lineHeight = 16.sp,
        )
        Text(
            text = from,
            fontSize = 26.sp,
        )
    }
}

//@Composable
//fun GreetingText(message: String, from: String, modifier: Modifier = Modifier) {
//    val image = painterResource(R.drawable.androidparty)
//    Image(
//        painter = image,
//        contentDescription = null
//    )
//}

@Preview(showBackground = true)
@Composable
fun BirthdayCardPreview() {
    HappyBirthdayTheme {
        GreetingImage(
            message = "Happy Birthday Sam!",
            from = "From Emma"
        )
    }
}

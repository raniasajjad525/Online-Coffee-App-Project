package com.example.onlinecoffeeapp.screens.welcomescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlinecoffeeapp.R



@Preview(showBackground = true)
@Composable
fun WelcomeScreen() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // This centers everything inside
    ) {
        // 1. Background image
        Image(
            painter = painterResource(id = R.drawable.splashscreen),
            contentDescription = "Welcome",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 2. Centered content - text + button
        Column(
            //Modifier.padding(24.dp),
            modifier = Modifier.offset(y= (-110).dp),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Text(
                text = "Coffee Online",
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Get started with your favourite coffee",
                fontSize = 16.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Inside WelcomeScreen.kt
            Button(
                onClick = {
                    // If using navigation:
                    // navController.navigate("login")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8B4513)
                ),
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text("Get Started", color = Color.White)
            }

        }
    }
}
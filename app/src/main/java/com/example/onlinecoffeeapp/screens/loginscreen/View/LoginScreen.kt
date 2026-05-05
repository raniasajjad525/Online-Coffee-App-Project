package com.example.onlinecoffeeapp.screens.loginscreen.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onlinecoffeeapp.viewmodel.AuthState
import com.example.onlinecoffeeapp.viewmodel.AuthViewModel

val BrownBackground = Color(0xFF8D5543)
val BrownHeader = Color(0xFFB37459)
val TextLabelColor = Color(0xFFE0C3B8)
val ButtonTextColor = Color(0xFF5D3A2E)

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgetPasswordClick: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            onLoginSuccess()
            viewModel.resetState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BrownBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f)
                .background(
                    color = BrownHeader,
                    shape = RoundedCornerShape(bottomStart = 60.dp, bottomEnd = 60.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Login",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
        ) {
            Text(
                text = "Email:",
                color = TextLabelColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFD9D9D9).copy(alpha = 0.9f),
                    unfocusedContainerColor = Color(0xFFD9D9D9).copy(alpha = 0.9f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black
                ),
                shape = RoundedCornerShape(20.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Password:",
                color = TextLabelColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFD9D9D9).copy(alpha = 0.9f),
                    unfocusedContainerColor = Color(0xFFD9D9D9).copy(alpha = 0.9f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black
                ),
                shape = RoundedCornerShape(20.dp)
            )

            if (authState is AuthState.Error) {
                Text(
                    text = (authState as AuthState.Error).message,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            TextButton(
                onClick = onForgetPasswordClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = "Forget password",
                    color = TextLabelColor,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (authState is AuthState.Loading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Button(
                        onClick = { viewModel.login(email, password) },
                        modifier = Modifier
                            .width(180.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD9D9D9)
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(text = "Login", color = ButtonTextColor, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Button(
                        onClick = onSignUpClick,
                        modifier = Modifier
                            .width(180.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD9D9D9)
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(text = "Sign up", color = ButtonTextColor, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

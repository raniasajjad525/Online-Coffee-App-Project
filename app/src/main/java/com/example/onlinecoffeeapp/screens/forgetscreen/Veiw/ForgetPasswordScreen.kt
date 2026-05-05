package com.example.onlinecoffeeapp.screens.forgetscreen.Veiw

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onlinecoffeeapp.R
import com.example.onlinecoffeeapp.viewmodel.AuthState
import com.example.onlinecoffeeapp.viewmodel.AuthViewModel

// Using the same color palette as Login/SignUp
val BrownBackground = Color(0xFF8D5543)
val BrownHeader = Color(0xFFB37459)
val TextLabelColor = Color(0xFFE0C3B8)
val ButtonTextColor = Color(0xFF5D3A2E)
val FieldColor = Color(0xFFD9D9D9)

@Composable
fun ForgetPasswordScreen(
    onBackToSignIn: () -> Unit,
    onFacebookClick: () -> Unit = {},
    onGoogleClick: () -> Unit = {},
    viewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(authState) {
        if (authState is AuthState.ResetEmailSent) {
            Toast.makeText(context, "Reset link sent to your email!", Toast.LENGTH_LONG).show()
            viewModel.resetState()
            onBackToSignIn()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BrownBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Curved Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.32f)
                .background(
                    color = BrownHeader,
                    shape = RoundedCornerShape(bottomStart = 60.dp, bottomEnd = 60.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Reset",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Form Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
        ) {
            Text(
                text = "Email address:",
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
                    focusedContainerColor = FieldColor,
                    unfocusedContainerColor = FieldColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                shape = RoundedCornerShape(20.dp),
                placeholder = { Text("Enter your email", color = Color.Gray) },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = BrownBackground)
                },
                singleLine = true
            )

            if (authState is AuthState.Error) {
                Text(
                    text = (authState as AuthState.Error).message,
                    color = Color.Yellow,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Back to login",
                color = TextLabelColor,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { onBackToSignIn() }
            )

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (authState is AuthState.Loading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Button(
                        onClick = { viewModel.resetPassword(email) },
                        modifier = Modifier
                            .width(180.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = FieldColor),
                        shape = RoundedCornerShape(20.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                    ) {
                        Text(
                            text = "Send Link",
                            color = ButtonTextColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "or reset with",
                    color = TextLabelColor,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.facebook),
                        contentDescription = "Facebook",
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .clickable { onFacebookClick() }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "Google",
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .clickable { onGoogleClick() }
                    )
                }
            }
        }
    }
}
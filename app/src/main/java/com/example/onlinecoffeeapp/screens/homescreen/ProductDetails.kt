package com.example.onlinecoffeeapp.screens.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlinecoffeeapp.R
import com.example.onlinecoffeeapp.ui.theme.CreamBeige
import com.example.onlinecoffeeapp.ui.theme.LightBrown

@Preview
@Composable
fun ProductDetails() {
    Card(
        modifier = Modifier
            .width(width = 300.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(size= 16.dp )

    ) {
        Column(
            modifier = Modifier.padding(all = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 250.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.iris),
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(size = 24.dp))
                )
            }
            Spacer(modifier = Modifier.height(height = 8.dp))

            Text(
                text = "Coffee Name",
                style = typography.titleMedium.copy(
                    color = Color.Black,
                    fontWeight= FontWeight.SemiBold

                )
            )

            Spacer(modifier = Modifier.height(height = 4.dp))



            Text(
                text = "Coffee Description ",
                style = typography.bodySmall.copy(
                    color = Color.Gray
                ),
                maxLines = 1,
                overflow =  TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(height = 20.dp))

           Row(
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceBetween,
               verticalAlignment = Alignment.CenterVertically
           ){
               Text(
                   text = "Product Price",
                   style = typography.titleMedium.copy(
                       fontWeight = FontWeight.Bold,
                       color =CreamBeige
                   )
               )

               IconButton(
                   onClick = { },
                   modifier = Modifier.background(
                       color = CreamBeige,
                       shape = RoundedCornerShape(size = 10.dp)
                   )
               ) {
                   Icon(
                       imageVector = Icons.Default.Add,
                       contentDescription = "Add to cart",
                       tint = Color.White

                   )
               }
           }


        }
    }
}
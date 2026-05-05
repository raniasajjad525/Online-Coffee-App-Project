package com.example.onlinecoffeeapp.screens.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.onlinecoffeeapp.ui.theme.CreamBeige
import com.example.onlinecoffeeapp.ui.theme.LightBrown

@Composable
fun CatagoriesList (
    text : String,
    isSelected: Boolean,
    onSelected: () -> Unit
){
    Box(
        modifier = Modifier
            .width(width = 90.dp)
            .height(height = 30.dp)
            .clip(shape= RoundedCornerShape(size= 6.dp))
            .clickable{ onSelected() }
            //.padding(vertical = 8.dp , horizontal =4.dp)
            .background(
                color = if (isSelected) CreamBeige else  LightBrown.copy(alpha = 0.6f)),
        contentAlignment = Alignment.Center

    ){
        Text(text = text,
            fontSize = 14.sp,
            fontWeight= FontWeight.SemiBold,
            maxLines = 1


        )
    }


}
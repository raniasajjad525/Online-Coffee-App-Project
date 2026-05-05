package com.example.onlinecoffeeapp.screens.homescreen.Veiw

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
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

@Composable
fun CatagoriesList (
    text : String,
    isSelected: Boolean,
    onSelected: () -> Unit
){
    val creamBeige = Color(0xFFC67C4E)
    val lightBrown = Color(0xFFEDD6C8)

    Box(
        modifier = Modifier
            .width(width = 90.dp)
            .height(height = 30.dp)
            .clip(shape= RoundedCornerShape(size= 6.dp))
            .clickable{ onSelected() }
            .background(
                color = if (isSelected) creamBeige else lightBrown.copy(alpha = 0.6f)),
        contentAlignment = Alignment.Center
    ){
        Text(text = text,
            fontSize = 14.sp,
            fontWeight= FontWeight.SemiBold,
            maxLines = 1,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

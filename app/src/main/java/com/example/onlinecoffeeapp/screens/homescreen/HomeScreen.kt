package com.example.onlinecoffeeapp.screens.homescreen

import android.location.Location
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlinecoffeeapp.R
import com.example.onlinecoffeeapp.screens.ui_components.MyBottomNavBar

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen(){

    val location = "Iqbal town,Lahore"

    Scaffold(
        bottomBar = { MyBottomNavBar() }
    ) {
        innerPadding ->

        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .fillMaxHeight(fraction = 1f / 3f)
            .background(
                color = Color(0xFF8A5A36)
                )

            )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp)
                .padding(paddingValues = innerPadding)
        ){

            Text(
                text = "Location",
                color = Color.White,
                fontSize = 14.sp
            )

            Spacer(modifier =  Modifier.height(height= 4.dp))

            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = location,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                /*
                Text(
                text = "User",
                color = Color.White
                )
                */
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Change Location",
                    tint = Color.White
                )
            }

            Spacer(modifier =  Modifier.height(height= 30.dp))

            MySearchBar()

            Spacer(modifier =  Modifier.height(height= 40.dp))

            Image(
                painter = painterResource(id = R.drawable.ban),
                contentDescription = "Home Banner"
            )

            Spacer(modifier =  Modifier.height(height= 16.dp))

            HomeCatagories()

            //Displaying Products
            val products = listOf(
                Product(id = 1, name= "Espresso", description= "Strong And rich", price= 500, imagesRes= R.drawable.expresso),
                Product(id = 2, name= "Latte", description= "Smooth and creamy", price= 300, imagesRes= R.drawable.latte),
                Product(id = 3, name= "Mocha", description= "Strong And smooth", price= 400, imagesRes= R.drawable.mocha),
                Product(id = 4, name= "Lungo", description= "With chocolate", price= 550, imagesRes= R.drawable.lungo),
                Product(id = 5, name= "Iris", description= "Velvety smooth", price= 450, imagesRes= R.drawable.iris),



            )
        }
    }

}

@Composable
fun Product(id: Int, name: String, description: String, price: Int, imagesRes: Int) {
    TODO("Not yet implemented")
}
package com.example.onlinecoffeeapp.screens.homescreen.Veiw

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.onlinecoffeeapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = {
                Text(text = "Search coffee...", color = Color.Gray)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Gray
                )
            },
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 0.dp,
                bottomStart = 16.dp,
                bottomEnd = 0.dp
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF0F0F0),
                unfocusedContainerColor = Color(0xFFF0F0F0),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Gray,
                cursorColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .height(60.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = onFilterClick,
            modifier = Modifier
                .size( width = 50.dp , height = 58.dp)
                .background(
                    color = Color(0xFF6E4428),
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 16.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 16.dp
                    )
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.list),
                contentDescription = "Filter",
                tint = Color.White
            )
        }
    }
}

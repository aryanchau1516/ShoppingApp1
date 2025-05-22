package com.example.shopping1.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun CartItemView(modifier: Modifier = Modifier,productId: String, quantity: Long) {
    Text(text = productId+ "---->"+quantity)
}
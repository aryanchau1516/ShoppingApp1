package com.example.shopping1.pages

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CategoriesProductPages(modifier: Modifier = Modifier,categoryId : String) {

    Text(text = "This is category page of $categoryId")
}
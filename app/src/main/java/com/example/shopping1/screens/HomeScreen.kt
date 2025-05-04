package com.example.shopping1.screens

import androidx.collection.mutableObjectIntMapOf
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.shopping1.pages.CartPage
import com.example.shopping1.pages.FavoritePage
import com.example.shopping1.pages.HomePage
import com.example.shopping1.pages.ProfilePage

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {
    val navItemList = listOf(
        NavItems("Home", Icons.Default.Home),
        NavItems("Favourite", Icons.Default.Favorite),
        NavItems("Cart", Icons.Default.ShoppingCart),
        NavItems("Profile", Icons.Default.Person),

        )
    var selectedIndexed by remember { mutableStateOf(0) }
    Scaffold(
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItems ->
                    NavigationBarItem(selected = index == selectedIndexed, onClick = {
                        selectedIndexed = index
                    }, icon = {
                        Icon(imageVector = navItems.icon, contentDescription = navItems.label)

                    }, label = {
                        Text(text = navItems.label)
                    }

                    )

                }
            }
        }
    ) {
        ContentScreen(modifier = modifier.padding(it),selectedIndexed)
    }
}


@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndexed: Int) {  //  this is for going to pages using switch cases
    when(selectedIndexed){
        0-> HomePage(modifier)
        1-> FavoritePage(modifier)
        2-> CartPage(modifier)
        3-> ProfilePage(modifier)
    }
}

data class NavItems(
    val label: String,
    val icon: ImageVector
)
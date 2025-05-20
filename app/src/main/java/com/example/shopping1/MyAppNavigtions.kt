package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screens.AuthScreen
import com.example.myapplication.screens.LoginScreen
import com.example.myapplication.screens.SignUpScreen
import com.example.shopping1.pages.CategoriesProductPages
import com.example.shopping1.pages.ProductDetailPage
import com.example.shopping1.screens.HomeScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    GlobalNavigation.navController = navController
    val isLoggedIn = Firebase.auth.currentUser!=null // checking user logged in or not
    var firstPage = if(isLoggedIn) "home" else "auth" //
    NavHost(navController=navController, startDestination = firstPage){// first is start destination
        composable("auth") {
            AuthScreen(modifier,navController)
        }
        composable("login") {
            LoginScreen(modifier,navController)
        }
        composable("signup") {
            SignUpScreen(modifier,navController)
        }
        composable("home") {
            HomeScreen(modifier,navController)
        }
        composable("categoryPage/{categoryId}") {
            var categoryId = it.arguments?.getString("categoryId")
            CategoriesProductPages( categoryId=categoryId?:"")
        }
        composable("ProductDetailPage/{productId}") {
            var productId = it.arguments?.getString("productId")
            ProductDetailPage( productId=productId?:"")
        }

    }
}

object GlobalNavigation{
    lateinit var navController: NavController
}
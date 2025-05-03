package com.example.shopping1.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun HomeScreen(modifier: Modifier = Modifier,navController: NavController) {

    Column {
        Text("Home Screen")
        Button(onClick = {
            Firebase.auth.signOut()
            navController.navigate("auth"){
                popUpTo("home"){
                    inclusive = true
                }
            }
        }) {

            Text("Logout" , fontSize = 22.sp)
        }
    }
}
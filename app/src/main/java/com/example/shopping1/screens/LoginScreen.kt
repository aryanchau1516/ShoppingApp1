package com.example.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.viewModel.AuthViewModel

import com.example.shopping1.R

@Composable
fun LoginScreen(modifier: Modifier = Modifier,navController: NavController ,authViewModel: AuthViewModel = viewModel()) {

    var email by remember { mutableStateOf("") }
    var passward by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome Back!",
            modifier = modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,

                )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Sign in to your account ",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 22.sp,
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(R.drawable.a3),
            contentDescription = "banner",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp).clip(RoundedCornerShape(100))
        )
        Spacer(modifier = Modifier.height(20.dp))


        OutlinedTextField(value = email, onValueChange = {
            email = it
        }, label = {
            Text(text = "email")
        }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(value = passward, onValueChange = {
            passward = it
        }, label = {
            Text(text = "passward")
        }, modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier=Modifier.height(20.dp))
        Button(onClick =  {
            isLoading = true
            authViewModel.login(email,passward){success,errorMessage->
                if (success){
                    isLoading = false
                    navController.navigate("home"){
                        popUpTo("auth"){// for go to auth screen
                            inclusive=true //for clear all back stack
                        }

                    }
                }
                else{
                    isLoading = false
                    AppUtil.showToast(context,errorMessage?:"Something is went wrong")
                }
            }
        },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)) {
            Text(text = if(isLoading) "logging in account" else "log in", fontSize = 22.sp)
        }
    }
}
package com.example.shopping1.pages

import UserModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.GlobalNavigation
import com.example.shopping1.component.CartItemView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

@Composable
fun CartPage(modifier: Modifier = Modifier) {
    val userModel = remember { mutableStateOf(UserModel()) }

    DisposableEffect(key1 = Unit) {
        val listener = Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val result = snapshot.toObject(UserModel::class.java)
                    if (result != null) {
                        userModel.value = result
                    }
                }
            }
        onDispose {
            listener.remove()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {
        Text(
            text = "Your Cart",
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        )
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(userModel.value.cartItems.toList(), key = { it.first }) { (productId, qty) ->
                CartItemView(productId = productId, quantity = qty)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))  // Spacer to separate list and button

        Button(
            onClick = { GlobalNavigation.navController.navigate("checkout") },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Checkout", color = Color.White)
        }
    }
}

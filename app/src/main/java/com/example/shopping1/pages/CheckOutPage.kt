package com.example.shopping1.pages

import UserModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopping1.model.ProductModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import java.nio.file.WatchEvent

@Composable
fun CheckOutPage(modifier: Modifier = Modifier) {
    val userModel = remember { mutableStateOf(UserModel()) }
    val productList = remember { mutableStateListOf<ProductModel>() }
    val subTotal = remember { mutableFloatStateOf(0f) }
    val discount = remember { mutableFloatStateOf(0f) }
    val tax = remember { mutableFloatStateOf(0f) }
    val totalAmount = remember { mutableFloatStateOf(0f) }

    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnSuccessListener { document ->
                val result = document.toObject(UserModel::class.java)
                if (result != null) {
                    userModel.value = result

                    Firebase.firestore.collection("data")
                        .document("stock").collection("products")
                        .whereIn("id", userModel.value.cartItems.keys.toList())
                        .get().addOnSuccessListener { task ->
                            val resultProduct = task.toObjects(ProductModel::class.java)
                            productList.clear()
                            productList.addAll(resultProduct)
                        }
                }
            }
    }

    // Auto-update subtotal when productList is updated
    LaunchedEffect(productList.size) {
        var total = 0f
        productList.forEach {
            if (it.actualPrice.isNotEmpty()) {
                val qty = userModel.value.cartItems[it.id] ?: 0
                val price = it.actualPrice.replace("₹", "").replace(",", "").toFloat()
                total += price * qty
            }
        }
        subTotal.floatValue = total
        discount.floatValue = subTotal.floatValue * 10 / 100
        tax.floatValue = subTotal.floatValue * 13 / 100
        val total2 = subTotal.floatValue - discount.floatValue + tax.floatValue
        totalAmount.floatValue = String.format("%.2f", total2).toFloat()

       // totalAmount.floatValue = subTotal.floatValue - discount.floatValue + tax.floatValue
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Checkout",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text="Deliver to ", fontWeight = FontWeight.SemiBold)
        Text(text = userModel.value.name)
        Text(text = userModel.value.address)
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))
//        Text(text="SubTotal :")
//        Text(text = "₹${subTotal.floatValue.toString()}")
        RowCheckedItems(title = "SubTotal", value = "₹${subTotal.floatValue.toString()}")
        Spacer(modifier = modifier.height(8.dp))
        RowCheckedItems(title = "Discount (-)", value = "₹${discount.floatValue.toString()}")
        Spacer(modifier = modifier.height(8.dp))
        RowCheckedItems(title = "Tax (+)", value = "₹${discount.floatValue.toString()}")
        Spacer(modifier = modifier.height(8.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "To Pay ",
            textAlign = TextAlign.Center
        )
        Text(modifier =Modifier.fillMaxWidth(),
            text = "₹${totalAmount.floatValue.toString()}",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
    }
}

@Composable
fun RowCheckedItems(title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Text(text = value, fontSize = 18.sp)
    }
}

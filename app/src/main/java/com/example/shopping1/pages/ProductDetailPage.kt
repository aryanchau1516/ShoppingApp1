package com.example.shopping1.pages

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.shopping1.model.ProductModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.unit.dp
import com.example.shopping1.model.CategoriesModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


@Composable
fun ProductDetailPage(modifier: Modifier = Modifier,productId: String) {

    var product by remember { mutableStateOf(ProductModel()) }
    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("data")
            .document("stock").
            collection("products").document(productId)
            .get().addOnCompleteListener() {
                if(it.isSuccessful){
                    val result = it .result.toObject(ProductModel::class.java)
                    if (result!=null){
                        product = result
                    }
                }
            }

    }

    Text(text = product.toString())

}
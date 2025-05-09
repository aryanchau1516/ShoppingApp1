package com.example.shopping1.pages

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.shopping1.model.CategoriesModel
import com.example.shopping1.model.ProductModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun CategoriesProductPages(modifier: Modifier = Modifier, categoryId: String) {

    val productList = remember {
        mutableStateOf<List<ProductModel>>(emptyList())
    }
    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("data")
            .document("stock").collection("products")
            .whereEqualTo("categories",categoryId)
            .get().addOnCompleteListener() {
                if (it.isSuccessful) {
                    val resultList = it.result.documents.mapNotNull {
                        it.toObject(ProductModel::class.java)
                    }
                    productList.value = resultList
                }
            }

    }

    LazyColumn {
        items(productList.value){
            Text(text = it.title)
        }
    }
}
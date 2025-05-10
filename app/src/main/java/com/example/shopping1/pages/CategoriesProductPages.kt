package com.example.shopping1.pages

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shopping1.component.ProductItemView
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
            .whereEqualTo("categories", categoryId)
            .get().addOnCompleteListener() {
                if (it.isSuccessful) {
                    val resultList = it.result.documents.mapNotNull {
                        it.toObject(ProductModel::class.java)
                    }
                    productList.value = resultList
                }
            }

    }

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        items(productList.value.chunked(2)) {rowItems->
            Row {
                rowItems.forEach {
                    ProductItemView(productModel = it, modifier = Modifier.weight(1f))

                }
                if (rowItems.size==1){
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
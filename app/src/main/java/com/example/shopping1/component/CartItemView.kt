package com.example.shopping1.component

import AppUtil.addItem
import AppUtil.removeItemCart
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.myapplication.GlobalNavigation
import com.example.shopping1.model.ProductModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


@Composable
fun CartItemView(modifier: Modifier = Modifier, productId: String, quantity: Long) {
    var product by remember { mutableStateOf(ProductModel()) }
    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("data").document("stock").collection("products")
            .document(productId).get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result.toObject(ProductModel::class.java)
                    if (result != null) {
                        product = result
                    }
                }
            }
    }
    val context = LocalContext.current
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                GlobalNavigation.navController.navigate("ProductDetailPage/" + product.id)
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(10.dp)

    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            AsyncImage(
                model = product.images.firstOrNull(),
                contentDescription = product.title,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
            )

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = product.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,

                    )
                Text(
                    text = product.actualPrice,
                    color = Color.Black,
                    fontSize = 16.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        removeItemCart(productID = product.id, context = context)
                    }) {
                        Text(text = "-", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(text = "$quantity", fontSize = 16.sp)
                    IconButton(onClick = {
                        addItem(productID = product.id, context = context)
                    }) {
                        Text(text = "+", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                }

            }
            IconButton(onClick = {
                removeItemCart(productID = product.id, context = context,removeAll = true)
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null
                )
            }


        }
    }
}
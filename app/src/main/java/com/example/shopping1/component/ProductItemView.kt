package com.example.shopping1.component

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
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
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

@Composable
fun ProductItemView(modifier: Modifier, productModel: ProductModel) {
    val context = LocalContext.current
    Card(
        modifier = modifier.padding(8.dp).clickable{
            GlobalNavigation.navController.navigate("ProductDetailPage/"+productModel.id)
        },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(10.dp)

    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            AsyncImage(
                model = productModel.images.firstOrNull(),
                contentDescription = productModel.title,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
            )
            Text(
                text = productModel.title,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp)
            )

            Row(
                modifier = Modifier // ✅ use fresh Modifier here
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = productModel.price,
                    color = Color.Black,
                    fontSize = 14.sp,
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )
                Spacer(modifier= Modifier.width(8.dp))
                Text(
                    text = productModel.actualPrice,
                    color = Color.Black,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    AppUtil.addItem(productID = productModel.id, context = context)
                }) {
                  Icon(
                      imageVector = Icons.Default.ShoppingCart,
                      contentDescription = null
                  )
                }

            }


        }
    }
}
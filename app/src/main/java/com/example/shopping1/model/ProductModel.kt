package com.example.shopping1.model

import android.icu.text.CaseMap

data class ProductModel(
    val id : String = " " ,
    val title : String = " ",
    val description : String = " ",
    val categories : String = " ",
    val price : String =" ",
    val actualPrice : String = "",
    val images: List<String> = emptyList(),
    val othervalue : Map<String,String> = mapOf()
)

package com.example.myapplication.model

import android.provider.ContactsContract.CommonDataKinds.Email

data class UserModel(
    val  name: String,
    val email: String,
    val uid : String
)

package com.example.myapplication.viewModel

import UserModel
import androidx.lifecycle.ViewModel

import com.google.firebase.Firebase

import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class AuthViewModel : ViewModel(){
    private  val auth = Firebase.auth
    private val firestore = Firebase.firestore
    fun login(email: String,password: String,onResult :(Boolean, String?)-> Unit){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    onResult(true,null)
                }
                else{
                    onResult(false,it.exception?.localizedMessage)
                }
            }

    }

    fun signUp(name:String,email:String,password:String,onResult:(Boolean,String?)->Unit){
        auth.createUserWithEmailAndPassword(email, password)  // using email and password we login
        .addOnCompleteListener{// it is use for becausse email and password are suspendable so it is add on complete
                if(it.isSuccessful){
                    var userId = it.result?.user?.uid  // when ever user llogin it assign a unique id
                    val usermodell = UserModel(name,email,userId!!)
                    firestore.collection("users").document(userId)
                        .set(usermodell)
                        .addOnCompleteListener{ dbTask->
                            if (dbTask .isSuccessful){
                                onResult(true,null)
                            }
                            else{
                                onResult(false,"Something is went weong")
                            }

                        }

                }
            else{
                onResult(false, it.exception?.localizedMessage)
                }

        }
    }
}


/*
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.myapplication.model.UserModel

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun login() {
        // Your login logic here
    }

    fun signUp(email: String, name: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = task.result?.user?.uid
                    val userModel = UserModel(name, email, userId!!)
                    firestore.collection("users").document(userId)
                        .set(userModel)
                        .addOnCompleteListener { dbTask ->
                            if (dbTask.isSuccessful) {
                                onResult(true, null)
                            } else {
                                onResult(false, "Something went wrong with Firestore.")
                            }
                        }
                } else {
                    onResult(false, task.exception?.localizedMessage)
                }
            }
    }
}




*
 */


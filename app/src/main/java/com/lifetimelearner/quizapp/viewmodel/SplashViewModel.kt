package com.lifetimelearner.quizapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.MutableData
import com.google.firebase.ktx.Firebase

class SplashViewModel: ViewModel() {

    val userLogin = MutableLiveData<Boolean>()

    fun signUpUser(stringName: String, stringEmail: String, stringPassword: String) {
        Firebase.auth.createUserWithEmailAndPassword(stringEmail, stringPassword)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    userLogin.postValue(true)
                }
            }
            .addOnFailureListener {
                Log.d("LOGGING", it.message)
            }
    }

    fun loginUser(stringEmail: String, stringPassword: String) {
        Firebase.auth.signInWithEmailAndPassword(stringEmail, stringPassword)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    userLogin.postValue(true)
                }
            }
            .addOnFailureListener {
                Log.d("LOGGING", it.message)
            }
    }

}
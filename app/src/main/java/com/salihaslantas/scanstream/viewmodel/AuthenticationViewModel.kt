package com.salihaslantas.scanstream.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.salihaslantas.scanstream.models.CurrentUserModel
import com.salihaslantas.scanstream.view.screens.OverviewScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthenticationViewModel(application: Application) : AndroidViewModel(application) {
    val auth = Firebase.auth
    val db = Firebase.firestore


    private val _signInState = MutableStateFlow<SignInResult>(SignInResult.Idle)
    val signInState: StateFlow<SignInResult> get() = _signInState

    fun signIn(email: String, password: String, onResult: (String?) -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            _signInState.value = SignInResult.Error("Email and password cannot be empty.")
            return
        }
        _signInState.value = SignInResult.Loading
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid ?: ""
                        checkUserRole(userId, onResult)
                        _signInState.value = SignInResult.Success
                    } else {
                        onResult(null)
                        _signInState.value = SignInResult.Error(task.exception?.message ?: "Unknown error")
                    }
                }
        }
    }

    fun checkUserRole(userId: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val role = document.getString("role") ?: ""
                        onResult(role)
                    } else {
                        onResult(null)
                    }
                }
                .addOnFailureListener {
                    onResult(null)
                }
        }
    }

    fun signOut() {
        auth.signOut()
        println("Sign out")
    }
    suspend fun userCardBuilder(firestore: FirebaseFirestore, storage: FirebaseStorage, userId: String): CurrentUserModel {
        return withContext(Dispatchers.IO) {
            try {
                val document = Tasks.await(firestore.collection("users").document(userId).get())

                if (document.exists()) {
                    CurrentUserModel(
                        name = document.getString("name") ?: "",
                        role = document.getString("role") ?: "",
                        photo = document.getString("photoUrl") ?: ""
                    )
                } else {
                    CurrentUserModel("", "", "")
                }
            } catch (e: Exception) {
                println(e.localizedMessage)
                CurrentUserModel("", "", "")
            }
        }
    }
}
sealed class SignInResult {
    object Idle : SignInResult()
    object Loading : SignInResult()
    object Success : SignInResult()
    data class Error(val message: String) : SignInResult()
}

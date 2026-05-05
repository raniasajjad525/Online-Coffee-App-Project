package com.example.onlinecoffeeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
    object ResetEmailSent : AuthState()
}

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    val currentUser = auth.currentUser

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email and Password cannot be empty")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _authState.value = AuthState.Success
                        } else {
                            _authState.value = AuthState.Error(task.exception?.message ?: "Login failed")
                        }
                    }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun signUp(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email and Password cannot be empty")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _authState.value = AuthState.Success
                        } else {
                            _authState.value = AuthState.Error(task.exception?.message ?: "Signup failed")
                        }
                    }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun resetPassword(email: String) {
        if (email.isEmpty()) {
            _authState.value = AuthState.Error("Email cannot be empty")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _authState.value = AuthState.ResetEmailSent
                        } else {
                            _authState.value = AuthState.Error(task.exception?.message ?: "Failed to send reset email")
                        }
                    }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }

    fun logout() {
        auth.signOut()
        _authState.value = AuthState.Idle
    }
}

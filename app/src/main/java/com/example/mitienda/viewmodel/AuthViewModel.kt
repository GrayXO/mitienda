package com.example.mitienda.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun login(email: String, pass: String) {
        if (email.isNotBlank() && pass.isNotBlank()) {
            _authState.value = AuthState.Success
        } else {
            _authState.value = AuthState.Error("Por favor completa todos los campos")
        }
    }

    fun register(email: String, pass: String) {
        if (email.isNotBlank() && pass.isNotBlank()) {
            _authState.value = AuthState.Success
        } else {
            _authState.value = AuthState.Error("Por favor completa todos los campos")
        }
    }

    fun logout() {
        _authState.value = AuthState.Idle
    }
}

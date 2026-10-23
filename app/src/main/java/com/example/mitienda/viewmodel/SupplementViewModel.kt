package com.example.mitienda.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mitienda.data.local.SupplementDatabase
import com.example.mitienda.data.local.SupplementEntity
import com.example.mitienda.data.repository.SupplementRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class SupplementViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SupplementRepository

    val supplements: StateFlow<List<SupplementEntity>>

    // Estado para el Carrito de Compras
    var cartItems by mutableStateOf<List<SupplementEntity>>(emptyList())
        private set

    // Producto seleccionado para ver detalles en el Home
    var selectedSupplement by mutableStateOf<SupplementEntity?>(null)
        private set

    init {
        val supplementDao = SupplementDatabase.getDatabase(application).supplementDao()
        repository = SupplementRepository(supplementDao)
        supplements = repository.allSupplements.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun addToCart(supplement: SupplementEntity) {
        cartItems = cartItems + supplement
    }

    fun removeFromCart(supplement: SupplementEntity) {
        cartItems = cartItems - supplement
    }

    fun selectSupplement(supplement: SupplementEntity) {
        selectedSupplement = supplement
    }
}
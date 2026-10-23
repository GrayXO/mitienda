package com.example.mitienda.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Store
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomNavItem("home", "Inicio", Icons.Default.Home)
    object Catalog : BottomNavItem("catalog", "Catálogo", Icons.Default.Store)
    object Cart : BottomNavItem("cart", "Carrito", Icons.Default.ShoppingCart)
    object Profile : BottomNavItem("profile", "Perfil", Icons.Default.Person)
}

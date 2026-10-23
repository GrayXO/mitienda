package com.example.mitienda.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mitienda.ui.maps.MapScreen
import com.example.mitienda.viewmodel.AuthViewModel
import com.example.mitienda.viewmodel.SupplementViewModel

@Composable
fun MainScreen(
    authViewModel: AuthViewModel,
    supplementViewModel: SupplementViewModel,
    onLogout: () -> Unit,
    onNavigateToMap: (() -> Unit)? = null // Parámetro opcional si navegas fuera del NavHost interno
) {
    val navController = rememberNavController()

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Catalog,
        BottomNavItem.Cart,
        BottomNavItem.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentRoute == item.route,
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(viewModel = supplementViewModel)
            }

            composable(BottomNavItem.Catalog.route) {
                CatalogScreen(
                    viewModel = supplementViewModel,
                    onNavigateToHome = {
                        navController.navigate(BottomNavItem.Home.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }

            composable(BottomNavItem.Cart.route) {
                CartScreen(
                    viewModel = supplementViewModel,
                    onCheckout = {
                        // Navega al mapa si la lambda externa existe, o usa una ruta interna "map"
                        if (onNavigateToMap != null) {
                            onNavigateToMap()
                        } else {
                            navController.navigate("map")
                        }
                    }
                )
            }

            composable(BottomNavItem.Profile.route) {
                ProfileScreen(onLogout = {
                    authViewModel.logout()
                    onLogout()
                })
            }

            // Ruta interna para la pantalla de mapa si la cargas dentro del Scaffold
            composable("map") {
                MapScreen()
            }
        }
    }
}
package com.example.mitienda.ui.main

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mitienda.viewmodel.SupplementViewModel

@Composable
fun CartScreen(
    viewModel: SupplementViewModel,
    onCheckout: () -> Unit = {}
) {
    val context = LocalContext.current
    val cartItems = viewModel.cartItems
    val totalPrice = cartItems.sumOf { it.price }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = "🛒 Carrito de Compras",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (cartItems.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Tu carrito está vacío.\n¡Agrega suplementos desde el catálogo!",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(cartItems) { item ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = item.name, style = MaterialTheme.typography.titleMedium)
                                    Text(text = "$${item.price} MXN", color = MaterialTheme.colorScheme.primary)
                                }
                                TextButton(onClick = { viewModel.removeFromCart(item) }) {
                                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Total a Pagar:", style = MaterialTheme.typography.titleMedium)
                            Text(text = "$$totalPrice MXN", style = MaterialTheme.typography.titleLarge)
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {

                                val gmmIntentUri = Uri.parse("geo:20.5888,-100.3899?q=Gimnasios+y+Suplementos")
                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                mapIntent.setPackage("com.google.android.apps.maps")

                                try {
                                    context.startActivity(mapIntent)
                                } catch (e: Exception) {

                                    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/?q=20.5888,-100.3899"))
                                    context.startActivity(webIntent)
                                }

                                onCheckout()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Finalizar Compra y Ver Sucursal")
                        }
                    }
                }
            }
        }
    }
}
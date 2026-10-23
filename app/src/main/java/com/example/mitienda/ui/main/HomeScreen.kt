package com.example.mitienda.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mitienda.viewmodel.SupplementViewModel

@Composable
fun HomeScreen(viewModel: SupplementViewModel = viewModel()) {
    val selectedSupplement = viewModel.selectedSupplement

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (selectedSupplement != null) {
                Text(text = "📋 Ficha Técnica del Producto", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(text = selectedSupplement!!.name, style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Badge { Text(text = selectedSupplement!!.category) }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "Descripción Ampliada:", style = MaterialTheme.typography.titleSmall)
                        Text(text = selectedSupplement!!.description, style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Precio: $${selectedSupplement!!.price} MXN",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Disponibilidad en almacén: ${selectedSupplement!!.stock} unidades",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            } else {
                Text(text = "🏠 Bienvenido a Tu Tienda de Suplementos", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Selecciona 'Detalles' en cualquier suplemento del catálogo para visualizar su información detallada aquí.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
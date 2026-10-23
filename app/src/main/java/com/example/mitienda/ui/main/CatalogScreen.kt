package com.example.mitienda.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mitienda.data.local.SupplementEntity
import com.example.mitienda.viewmodel.SupplementViewModel

@Composable
fun CatalogScreen(
    viewModel: SupplementViewModel, // Recibe la instancia compartida desde MainScreen
    onNavigateToHome: () -> Unit
) {
    val supplementsList by viewModel.supplements.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = "💪 Catálogo de Suplementos",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (supplementsList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(supplementsList) { supplement ->
                        SupplementCard(
                            supplement = supplement,
                            onBuyClick = {
                                viewModel.addToCart(supplement)
                            },
                            onDetailClick = {
                                viewModel.selectSupplement(supplement)
                                onNavigateToHome()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SupplementCard(
    supplement: SupplementEntity,
    onBuyClick: () -> Unit,
    onDetailClick: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = supplement.name, style = MaterialTheme.typography.titleMedium)
                Badge { Text(text = supplement.category) }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = supplement.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$${supplement.price} MXN",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = onDetailClick) {
                        Text("Detalles")
                    }
                    Button(onClick = onBuyClick) {
                        Text("Comprar")
                    }
                }
            }
        }
    }
}
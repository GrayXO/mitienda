package com.example.mitienda.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartEntity(
    @PrimaryKey(autoGenerate = true) val cartId: Int = 0,
    val name: String,
    val category: String,
    val price: Double,
    val description: String
)
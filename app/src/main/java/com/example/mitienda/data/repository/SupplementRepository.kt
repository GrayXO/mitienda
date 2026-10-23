package com.example.mitienda.data.repository

import com.example.mitienda.data.local.SupplementDao
import com.example.mitienda.data.local.SupplementEntity
import kotlinx.coroutines.flow.Flow

class SupplementRepository(private val supplementDao: SupplementDao) {
    val allSupplements: Flow<List<SupplementEntity>> = supplementDao.getAllSupplements()

    suspend fun insertInitialData(supplements: List<SupplementEntity>) {
        supplementDao.insertAll(supplements)
    }
}
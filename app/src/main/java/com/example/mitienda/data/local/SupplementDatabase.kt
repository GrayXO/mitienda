package com.example.mitienda.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [SupplementEntity::class, CartEntity::class], version = 2, exportSchema = false)
abstract class SupplementDatabase : RoomDatabase() {
    abstract fun supplementDao(): SupplementDao

    companion object {
        @Volatile
        private var INSTANCE: SupplementDatabase? = null

        fun getDatabase(context: Context): SupplementDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SupplementDatabase::class.java,
                    "supplement_database"
                )
                    .addCallback(SupplementDatabaseCallback())
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class SupplementDatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateDatabase(database.supplementDao())
                    }
                }
            }

            suspend fun populateDatabase(dao: SupplementDao) {
                val initialSupplements = listOf(
                    SupplementEntity(name = "Whey Protein 100% Gold", category = "Proteína", price = 1250.0, description = "Proteína isolada de suero de leche de alta calidad (2kg).", stock = 15),
                    SupplementEntity(name = "Creatina Monohidratada 500g", category = "Creatina", price = 650.0, description = "Aumenta la fuerza y el rendimiento muscular explosivo.", stock = 25),
                    SupplementEntity(name = "Pre-Workout Insane Labz", category = "Pre-entreno", price = 850.0, description = "Energía extrema y enfoque mental para entrenamientos pesados.", stock = 10),
                    SupplementEntity(name = "BCAA 2:1:1 Recuperación", category = "Aminoácidos", price = 550.0, description = "Aminoácidos ramificados para evitar el catabolismo muscular.", stock = 20)
                )
                dao.insertAll(initialSupplements)
            }
        }
    }
}

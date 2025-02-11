package fr.unica.miage.numres.tabbaa.pizzapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.unica.miage.numres.tabbaa.pizzapp.model.OrderEntity
import fr.unica.miage.numres.tabbaa.pizzapp.utils.OrderConverters

/**
 * Classe qui définit la base de données Room pour Android
 */
@Database(entities = [OrderEntity::class], version = 2, exportSchema = false) // 🔥 Increment version
@TypeConverters(OrderConverters::class)
abstract class PizzaDatabase : RoomDatabase() {
    abstract fun orderDao(): AndroidOrderDao

    companion object {
        @Volatile
        private var INSTANCE: PizzaDatabase? = null

        fun getDatabase(context: Context): PizzaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PizzaDatabase::class.java,
                    "pizza_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

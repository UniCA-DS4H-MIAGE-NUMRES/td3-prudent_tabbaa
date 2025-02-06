package fr.unica.miage.tabbaa.pizzapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.unica.miage.tabbaa.pizzapp.model.Order
import fr.unica.miage.tabbaa.pizzapp.model.OrderEntity
import fr.unica.miage.tabbaa.pizzapp.util.OrderConverters

/**
 * Classe qui définit la base de données Room pour Android
 */
@Database(entities = [OrderEntity::class], version = 1, exportSchema = false)
@TypeConverters(OrderConverters::class)
abstract class PizzaDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var INSTANCE: PizzaDatabase? = null

        fun getDatabase(context: Context): PizzaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PizzaDatabase::class.java,
                    "pizza_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

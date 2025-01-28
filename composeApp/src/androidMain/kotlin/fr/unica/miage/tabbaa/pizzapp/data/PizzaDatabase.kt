package fr.unica.miage.tabbaa.pizzapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.unica.miage.tabbaa.pizzapp.model.Order
import fr.unica.miage.tabbaa.pizzapp.util.OrderConverters

/**
 * Classe qui définit la base de données Room
 */
@Database(entities = [Order::class], version = 1, exportSchema = false) // Entité Order
@TypeConverters(OrderConverters::class) // Convertisseur personnalisé
abstract class PizzaDatabase : RoomDatabase() {

    /**
     * Méthode pour obtenir le DAO
     */
    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var INSTANCE: PizzaDatabase? = null

        /**
         * Méthode pour obtenir une instance de la base de données
         * @return PizzaDatabase : instance de la base de données
         */
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

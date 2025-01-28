package fr.unica.miage.tabbaa.pizzapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.unica.miage.tabbaa.pizzapp.model.Order
import kotlinx.coroutines.flow.Flow

/**
 * Interface pour les opérations sur la table des commandes
 */
@Dao
interface OrderDao {
    /**
     * Méthode pour insérer une commande
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)

    /**
     * Méthode pour obtenir toutes les commandes
     */
    @Query("SELECT * FROM orders ORDER BY orderId DESC")
    fun getAllOrders(): Flow<List<Order>>

    /**
     * Méthode pour supprimer toutes les commandes
     */
    @Query("DELETE FROM orders")
        suspend fun clearOrders()
}

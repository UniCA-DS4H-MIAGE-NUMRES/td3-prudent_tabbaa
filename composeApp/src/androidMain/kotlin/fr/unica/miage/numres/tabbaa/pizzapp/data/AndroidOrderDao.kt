package fr.unica.miage.numres.tabbaa.pizzapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.unica.miage.numres.tabbaa.pizzapp.model.OrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AndroidOrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Query("SELECT * FROM orders ORDER BY orderId DESC")
    fun getAllOrders(): Flow<List<OrderEntity>>

    @Query("DELETE FROM orders")
    suspend fun deleteAllOrders()

    @Query("SELECT * FROM orders")
    suspend fun refreshOrders(): List<OrderEntity>
}

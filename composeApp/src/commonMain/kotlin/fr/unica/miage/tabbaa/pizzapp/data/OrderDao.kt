package fr.unica.miage.tabbaa.pizzapp.data

import fr.unica.miage.tabbaa.pizzapp.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderDao {
    suspend fun insertOrder(order: Order)
    fun getAllOrders(): Flow<List<Order>>
    suspend fun deleteAllOrders()
}

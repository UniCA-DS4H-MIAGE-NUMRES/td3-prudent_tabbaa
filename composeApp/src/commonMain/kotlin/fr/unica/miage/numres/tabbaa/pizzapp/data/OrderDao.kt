package fr.unica.miage.numres.tabbaa.pizzapp.data

import fr.unica.miage.numres.tabbaa.pizzapp.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderDao {
    suspend fun insertOrder(order: Order)
    fun getAllOrders(): Flow<List<Order>>
    suspend fun deleteAllOrders()
    suspend fun refreshOrders()
}

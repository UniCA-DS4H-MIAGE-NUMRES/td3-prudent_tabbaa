package fr.unica.miage.tabbaa.pizzapp.data

import fr.unica.miage.tabbaa.pizzapp.model.Order
import kotlinx.coroutines.flow.Flow

expect class OrderRepository(orderDao: OrderDao) {
    fun getOrders(): Flow<List<Order>>
    suspend fun addOrder(order: Order)
    suspend fun clearOrders()
}

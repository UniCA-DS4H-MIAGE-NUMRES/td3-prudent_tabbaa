package fr.unica.miage.tabbaa.pizzapp.data

import fr.unica.miage.tabbaa.pizzapp.model.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

actual class OrderRepository actual constructor(private val dao: OrderDao) {
    private val orders = MutableStateFlow<List<Order>>(emptyList())

    actual fun getOrders(): Flow<List<Order>> = dao.getAllOrders()

    actual suspend fun addOrder(order: Order) {
        dao.insertOrder(order)
    }

    actual suspend fun deleteAllOrders() {
        dao.deleteAllOrders()
    }
}

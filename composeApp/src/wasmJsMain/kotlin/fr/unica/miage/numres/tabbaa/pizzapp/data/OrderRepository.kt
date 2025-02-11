package fr.unica.miage.numres.tabbaa.pizzapp.data

import fr.unica.miage.numres.tabbaa.pizzapp.model.Order
import kotlinx.coroutines.flow.Flow

actual class OrderRepository actual constructor(private val dao: OrderDao) {
    actual fun getOrders(): Flow<List<Order>> = dao.getAllOrders()

    actual suspend fun addOrder(order: Order) {
        dao.insertOrder(order)
    }

    actual suspend fun deleteAllOrders() {
        dao.deleteAllOrders()
    }
}
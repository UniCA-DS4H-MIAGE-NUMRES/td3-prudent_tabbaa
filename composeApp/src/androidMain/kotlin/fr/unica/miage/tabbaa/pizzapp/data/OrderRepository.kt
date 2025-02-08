package fr.unica.miage.tabbaa.pizzapp.data

import fr.unica.miage.tabbaa.pizzapp.model.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

actual class OrderRepository actual constructor(orderDao: OrderDao) {
    private val dao = orderDao

    actual fun getOrders(): Flow<List<Order>> = dao.getAllOrders()

    actual suspend fun addOrder(order: Order) {
        dao.insertOrder(order)
    }

    actual suspend fun clearOrders() {
        withContext(Dispatchers.IO) {
            dao.deleteAllOrders()
        }
    }
}

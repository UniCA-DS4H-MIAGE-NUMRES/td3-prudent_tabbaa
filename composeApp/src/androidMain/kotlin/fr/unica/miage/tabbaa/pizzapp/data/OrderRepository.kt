package fr.unica.miage.tabbaa.pizzapp.data

import fr.unica.miage.tabbaa.pizzapp.model.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

actual class OrderRepository actual constructor(private val dao: OrderDao) {
    actual fun getOrders(): Flow<List<Order>> = dao.getAllOrders()

    actual suspend fun addOrder(order: Order) {
        dao.insertOrder(order)
    }

    actual suspend fun deleteAllOrders() {
        withContext(Dispatchers.IO) {
            dao.deleteAllOrders()
        }
    }
}

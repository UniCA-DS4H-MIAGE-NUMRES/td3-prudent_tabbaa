package fr.unica.miage.tabbaa.pizzapp.data

import fr.unica.miage.tabbaa.pizzapp.model.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

actual class OrderRepository actual constructor(private val dao: OrderDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    actual fun getOrders(): Flow<List<Order>> {
        coroutineScope.launch {
            dao.refreshOrders()
        }
        return dao.getAllOrders()
    }

    actual suspend fun addOrder(order: Order) {
        withContext(Dispatchers.IO) {
            dao.insertOrder(order)
            dao.refreshOrders()
        }
    }

    actual suspend fun deleteAllOrders() {
        withContext(Dispatchers.IO) {
            dao.deleteAllOrders()
            dao.refreshOrders()
        }
    }
}

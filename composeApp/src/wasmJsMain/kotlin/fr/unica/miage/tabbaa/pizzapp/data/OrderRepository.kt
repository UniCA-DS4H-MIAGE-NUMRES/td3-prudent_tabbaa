package fr.unica.miage.tabbaa.pizzapp.data

import fr.unica.miage.tabbaa.pizzapp.model.Order
import kotlinx.coroutines.flow.Flow

actual class OrderRepository actual constructor(dao: OrderDao) {
    private val webDao = OrderDaoWeb()

    init {
        println("✅ OrderRepository initialized for Web")
    }

    actual fun getOrders(): Flow<List<Order>> = webDao.getAllOrders()

    actual suspend fun addOrder(order: Order) {
        println("➕ Adding order in Web: $order")
        webDao.insertOrder(order)
    }

    actual suspend fun deleteAllOrders() {
        println("🗑 Deleting all orders in Web")
        webDao.deleteAllOrders()
    }
}

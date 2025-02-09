package fr.unica.miage.tabbaa.pizzapp.data

import fr.unica.miage.tabbaa.pizzapp.model.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrderDaoWeb : OrderDao {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())

    init {
        println("✅ OrderDaoWeb initialized - Using in-memory storage")
    }

    override fun getAllOrders(): Flow<List<Order>> = _orders.asStateFlow()

    override suspend fun insertOrder(order: Order) {
        _orders.value = _orders.value + order
    }

    override suspend fun deleteAllOrders() {
        _orders.value = emptyList()
    }

    override suspend fun refreshOrders() {
        println("🔄 refreshOrders() called in Web - No action needed")
    }
}

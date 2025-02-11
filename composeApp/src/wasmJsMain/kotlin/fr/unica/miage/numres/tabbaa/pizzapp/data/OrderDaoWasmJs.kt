package fr.unica.miage.numres.tabbaa.pizzapp.data

import fr.unica.miage.numres.tabbaa.pizzapp.model.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class OrderDaoWasmJs : OrderDao {
    private val orders = MutableStateFlow<List<Order>>(emptyList())

    override suspend fun insertOrder(order: Order) {
        val currentOrders = orders.value.toMutableList()
        currentOrders.add(order)
        orders.value = currentOrders
    }

    override fun getAllOrders(): Flow<List<Order>> = orders

    override suspend fun deleteAllOrders() {
        orders.value = emptyList()
    }

    override suspend fun refreshOrders() {
        // Pas besoin d'implémentation pour WASM/JS car tout est en mémoire
    }
}
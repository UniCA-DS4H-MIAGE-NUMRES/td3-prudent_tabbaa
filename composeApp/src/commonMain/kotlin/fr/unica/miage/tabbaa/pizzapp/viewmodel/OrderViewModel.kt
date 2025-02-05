package fr.unica.miage.tabbaa.pizzapp.viewmodel

import fr.unica.miage.tabbaa.pizzapp.data.OrderRepository
import fr.unica.miage.tabbaa.pizzapp.model.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.Flow

class OrderViewModel(private val repository: OrderRepository) {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    fun loadOrders() {
        _orders.value = repository.getAllOrders()
    }

    fun placeOrder(order: Order) {
        repository.insertOrder(order)
        loadOrders()
    }
}

package fr.unica.miage.tabbaa.pizzapp.data

import fr.unica.miage.tabbaa.pizzapp.Order
import kotlinx.coroutines.flow.Flow

class OrderRepository(private val database: PizzaDatabase) {
    private val queries = database.ordersQueries

    fun insertOrder(order: Order) {
        queries.insertOrder(order.date, order.totalPrice, order.paymentMethod)
    }

    fun getAllOrders(): Flow<List<Order>> {
        return queries.selectAllOrders()
    }

    fun clearOrders() {
        queries.deleteAllOrders()
    }
}

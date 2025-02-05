package fr.unica.miage.tabbaa.pizzapp.data

import fr.unica.miage.tabbaa.pizzapp.OrderItem
import kotlinx.coroutines.flow.Flow

class OrderItemRepository(private val database: PizzaDatabase) {
    private val queries = database.orderItemsQueries

    fun insertOrderItem(orderId: Int, pizzaName: String, quantity: Int, extraCheese: Int) {
        queries.insertOrderItem(orderId, pizzaName, quantity, extraCheese)
    }

    fun getOrderItems(orderId: Int): Flow<List<OrderItem>> {
        return queries.selectOrderItemsByOrder(orderId)
    }

    fun deleteOrderItem(id: Int) {
        queries.deleteOrderItemById(id)
    }
}

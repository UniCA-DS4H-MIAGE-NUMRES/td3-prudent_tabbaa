package fr.unica.miage.tabbaa.pizzapp.data

import fr.unica.miage.tabbaa.pizzapp.model.Order
import fr.unica.miage.tabbaa.pizzapp.model.OrderItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.sql.Connection
import java.sql.DriverManager

class OrderDaoDesktop : OrderDao {
    private val orders = MutableStateFlow<List<Order>>(emptyList())

    init {
        createTable()
    }

    private fun createTable() {
        val connection = getConnection()
        connection.createStatement().execute("""
            CREATE TABLE IF NOT EXISTS orders (
                orderId INTEGER PRIMARY KEY AUTOINCREMENT,
                date TEXT,
                totalPrice REAL,
                paymentMethod TEXT,
                items TEXT
            )
        """)
        connection.close()
    }

    override suspend fun refreshOrders() {
        val connection = getConnection()
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM orders")
        val orderList = mutableListOf<Order>()

        while (resultSet.next()) {
            val order = Order(
                orderId = resultSet.getInt("orderId"),
                date = resultSet.getString("date"),
                totalPrice = resultSet.getDouble("totalPrice"),
                paymentMethod = resultSet.getString("paymentMethod"),
                items = decodeOrderItemsFromJson(resultSet.getString("items"))
            )
            orderList.add(order)
        }

        orders.value = orderList
        connection.close()
    }

    override suspend fun insertOrder(order: Order) {
        val connection = getConnection()
        val statement = connection.prepareStatement(
            "INSERT INTO orders (date, totalPrice, paymentMethod, items) VALUES (?, ?, ?, ?)"
        )
        statement.setString(1, order.date)
        statement.setDouble(2, order.totalPrice)
        statement.setString(3, order.paymentMethod)
        statement.setString(4, encodeOrderItemsToJson(order.items))
        statement.executeUpdate()
        connection.close()
        refreshOrders()
    }

    override fun getAllOrders(): Flow<List<Order>> = orders

    override suspend fun deleteAllOrders() {
        val connection = getConnection()
        connection.createStatement().execute("DELETE FROM orders")
        connection.close()
        refreshOrders()
    }

    private fun getConnection(): Connection {
        return DriverManager.getConnection("jdbc:sqlite:pizza_database.db")
    }
}

private fun encodeOrderItemsToJson(items: List<OrderItem>): String {
    return Json.encodeToString(items)
}

private fun decodeOrderItemsFromJson(json: String): List<OrderItem> {
    return Json.decodeFromString(json)
}

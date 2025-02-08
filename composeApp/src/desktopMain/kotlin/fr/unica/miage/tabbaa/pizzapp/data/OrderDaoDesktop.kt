package fr.unica.miage.tabbaa.pizzapp.data

import fr.unica.miage.tabbaa.pizzapp.model.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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
                paymentMethod TEXT
            )
        """)
        connection.close()
    }

    override suspend fun insertOrder(order: Order) {
        val connection = getConnection()
        val statement = connection.prepareStatement(
            "INSERT INTO orders (date, totalPrice, paymentMethod) VALUES (?, ?, ?)"
        )
        statement.setString(1, order.date)
        statement.setDouble(2, order.totalPrice)
        statement.setString(3, order.paymentMethod)
        statement.executeUpdate()
        connection.close()
    }

    override fun getAllOrders(): Flow<List<Order>> = orders

    override suspend fun deleteAllOrders() {
        val connection = getConnection()
        connection.createStatement().execute("DELETE FROM orders")
        connection.close()
    }

    private fun getConnection(): Connection {
        return DriverManager.getConnection("jdbc:sqlite:pizza_database.db")
    }
}

package fr.unica.miage.tabbaa.pizzapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import fr.unica.miage.tabbaa.pizzapp.util.OrderConverters

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val orderId: Int = 0,
    val date: String,
    val totalPrice: Double,
    val paymentMethod: String,
    @TypeConverters(OrderConverters::class) val items: List<OrderItem>
)

fun OrderEntity.toOrder(): Order {
    return Order(orderId, date, totalPrice, paymentMethod, items)
}

fun Order.toEntity(): OrderEntity {
    return OrderEntity(orderId, date, totalPrice, paymentMethod, items)
}


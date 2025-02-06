package fr.unica.miage.tabbaa.pizzapp.model

import kotlinx.serialization.Serializable

@Serializable
actual data class Order(
    actual val orderId: Int = 0,
    actual val date: String,
    actual val totalPrice: Double,
    actual val paymentMethod: String,
    actual val items: List<OrderItem>
)

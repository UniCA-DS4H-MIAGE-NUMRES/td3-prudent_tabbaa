package fr.unica.miage.tabbaa.pizzapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val orderId: Int = 0,
    val date: String,
    val totalPrice: Double,
    val paymentMethod: String,
    val items: List<OrderItem>
)

package fr.unica.miage.numres.tabbaa.pizzapp.model


data class Order(
    val orderId: Int = 0,
    val date: String,
    val totalPrice: Double,
    val paymentMethod: String,
    val items: List<OrderItem>
)

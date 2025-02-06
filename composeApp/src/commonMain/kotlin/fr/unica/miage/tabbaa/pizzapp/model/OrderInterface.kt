package fr.unica.miage.tabbaa.pizzapp.model

interface OrderInterface {
    val date: String
    val totalPrice: Double
    val paymentMethod: String
    val items: List<OrderItem>
}

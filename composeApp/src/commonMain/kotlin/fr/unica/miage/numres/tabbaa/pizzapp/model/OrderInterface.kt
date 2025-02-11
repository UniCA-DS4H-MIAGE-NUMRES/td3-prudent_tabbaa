package fr.unica.miage.numres.tabbaa.pizzapp.model

interface OrderInterface { // A SUPPRIMER ?
    val date: String
    val totalPrice: Double
    val paymentMethod: String
    val items: List<OrderItem>
}

package fr.unica.miage.numres.tabbaa.pizzapp.model

data class OrderItem(
    val id: Int,
    var pizza: Pizza,
    val quantity: Int,
    val extraCheese: Int
)

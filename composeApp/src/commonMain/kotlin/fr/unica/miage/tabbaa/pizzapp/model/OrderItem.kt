package fr.unica.miage.tabbaa.pizzapp.model

import kotlinx.serialization.Serializable
import fr.unica.miage.tabbaa.pizzapp.model.Pizza

@Serializable
data class OrderItem(
    val id: Int,
    val pizza: Pizza,
    val quantity: Int,
    val extraCheese: Int
)

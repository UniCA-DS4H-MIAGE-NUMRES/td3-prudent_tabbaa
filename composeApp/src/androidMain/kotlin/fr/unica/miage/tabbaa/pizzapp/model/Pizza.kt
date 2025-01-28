package fr.unica.miage.tabbaa.pizzapp.model

/**
 * Classe qui définit une pizza
 */
class Pizza(
    val name: String,
    val price: Double,
    val image: Int,
    val ingredients: List<String>
)
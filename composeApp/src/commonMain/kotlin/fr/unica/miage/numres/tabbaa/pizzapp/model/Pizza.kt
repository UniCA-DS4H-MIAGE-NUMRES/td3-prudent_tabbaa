package fr.unica.miage.numres.tabbaa.pizzapp.model

data class Pizza(
    val id: Int,
    val name: String,
    val price: Double,
    val image: String,
    val ingredients: List<String>
)
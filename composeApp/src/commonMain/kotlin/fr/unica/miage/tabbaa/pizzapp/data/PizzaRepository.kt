package fr.unica.miage.tabbaa.pizzapp.data

import fr.unica.miage.tabbaa.pizzapp.model.Pizza

class PizzaRepository {
    private val allPizzas = listOf(
        Pizza("Marguerita", 8.0, listOf("Tomate", "Mozzarella", "Basilic")),
        Pizza("Capricciosa", 10.0, listOf("Tomate", "Mozzarella", "Jambon", "Champignons")),
        Pizza("Diavola", 9.0, listOf("Tomate", "Mozzarella", "Salami épicé")),
        Pizza("Quattro Stagioni", 11.0, listOf("Tomate", "Mozzarella", "Artichauts", "Jambon", "Champignons")),
        Pizza("Quattro Formaggi", 12.0, listOf("Mozzarella", "Parmesan", "Gorgonzola", "Provolone")),
        Pizza("Marinara", 7.0, listOf("Tomate", "Ail", "Origan")),
        Pizza("Pepperoni", 9.0, listOf("Tomate", "Mozzarella", "Pepperoni")),
        Pizza("Prosciutto", 10.0, listOf("Tomate", "Mozzarella", "Jambon")),
        Pizza("Frutti di Mare", 13.0, listOf("Tomate", "Mozzarella", "Fruits de mer"))
    )

    fun getAllPizzas(): List<Pizza> = allPizzas

    fun getPizzaByName(name: String): Pizza? {
        return allPizzas.find { it.name == name }
    }
}

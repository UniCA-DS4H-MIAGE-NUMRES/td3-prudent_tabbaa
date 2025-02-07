package fr.unica.miage.tabbaa.pizzapp.data

import fr.unica.miage.tabbaa.pizzapp.model.Order
import fr.unica.miage.tabbaa.pizzapp.model.Pizza
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataSource(private val orderDao: OrderDao) {

    // Historique des commandes
    val orderHistory: Flow<List<Order>> = orderDao.getAllOrders()

    // Fonction pour sauvegarder une commande
    suspend fun saveOrder(order: Order) {
        orderDao.insertOrder(order)
    }

    // Fonction pour supprimer toutes les commandes
    suspend fun clearOrders() {
        orderDao.clearOrders()
    }

    // Fonction pour récupérer la liste des pizzas (FAUT METTRE LES VRAIES IMAGES A VOIR COMMENT FAIRE)
    fun getPizzas(): List<Pizza> {
        return listOf(
            Pizza("Margherita", 8.0, 1, listOf("Tomate", "Mozzarella", "Basilic")),
            Pizza("Diavola", 10.0, 2, listOf("Tomate", "Mozzarella", "Salami épicé")),
            Pizza("Quattro Stagioni", 11.0, 3, listOf("Tomate", "Mozzarella", "Jambon", "Champignons")),
            Pizza("Quattro Formaggi", 12.0, 4, listOf("Mozzarella", "Parmesan", "Gorgonzola", "Provolone")),
            Pizza("Pepperoni", 9.0, 5, listOf("Tomate", "Mozzarella", "Pepperoni"))
        )
    }
}

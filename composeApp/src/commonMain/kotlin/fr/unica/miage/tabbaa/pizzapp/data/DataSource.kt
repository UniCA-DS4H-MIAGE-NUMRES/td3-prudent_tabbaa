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
    suspend fun deleteAllOrders() {
        orderDao.deleteAllOrders()
    }

    // Fonction pour récupérer la liste des pizzas (FAUT METTRE LES VRAIES IMAGES A VOIR COMMENT FAIRE)
    fun getPizzas(): List<Pizza> {
        return listOf(
            Pizza(1, "Marguerita", 8.0, "pizza1.png", listOf("Tomate", "Mozzarella", "Basilic")),
            Pizza(2, "Capricciosa", 10.0, "pizza2.png", listOf("Tomate", "Mozzarella", "Jambon", "Champignons")),
            Pizza(3, "Diavola", 9.0, "pizza3.png", listOf("Tomate", "Mozzarella", "Salami épicé")),
            Pizza(4, "Quattro Stagioni", 11.0, "pizza4.png", listOf("Tomate", "Mozzarella", "Artichauts", "Jambon", "Champignons")),
            Pizza(5, "Quattro Formaggi", 12.0, "pizza5.png", listOf("Mozzarella", "Parmesan", "Gorgonzola", "Provolone")),
            Pizza(6, "Marinara", 7.0, "pizza6.png", listOf("Tomate", "Ail", "Origan")),
            Pizza(7, "Pepperoni", 9.0, "pizza7.png", listOf("Tomate", "Mozzarella", "Pepperoni")),
            Pizza(8, "Prosciutto", 10.0, "pizza8.png", listOf("Tomate", "Mozzarella", "Jambon")),
            Pizza(9 ,"Frutti di Mare", 13.0, "pizza9.png", listOf("Tomate", "Mozzarella", "Fruits de mer"))
        )


    }
}

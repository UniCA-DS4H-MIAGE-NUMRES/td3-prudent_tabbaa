package fr.unica.miage.tabbaa.pizzapp.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.unica.miage.tabbaa.pizzapp.R
import fr.unica.miage.tabbaa.pizzapp.model.Order
import fr.unica.miage.tabbaa.pizzapp.model.OrderItem
import fr.unica.miage.tabbaa.pizzapp.model.Pizza
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Classe qui gère les données de l'application
 */
class DataSource(private val orderDao: OrderDao) : ViewModel() {

    private var nextId = 1 // Pour générer des identifiants uniques pour les OrderItems
    private val TAG = "PizzaApp-Log" // Tag pour les logs

    // Liste de toutes les pizzas disponibles
    private val allPizzas = listOf(
        Pizza("Marguerita", 8.0, R.drawable.pizza1, listOf("Tomate", "Mozzarella", "Basilic")),
        Pizza("Capricciosa", 10.0, R.drawable.pizza2, listOf("Tomate", "Mozzarella", "Jambon", "Champignons")),
        Pizza("Diavola", 9.0, R.drawable.pizza3, listOf("Tomate", "Mozzarella", "Salami épicé")),
        Pizza("Quattro Stagioni", 11.0, R.drawable.pizza4, listOf("Tomate", "Mozzarella", "Artichauts", "Jambon", "Champignons")),
        Pizza("Quattro Formaggi", 12.0, R.drawable.pizza5, listOf("Mozzarella", "Parmesan", "Gorgonzola", "Provolone")),
        Pizza("Marinara", 7.0, R.drawable.pizza6, listOf("Tomate", "Ail", "Origan")),
        Pizza("Pepperoni", 9.0, R.drawable.pizza7, listOf("Tomate", "Mozzarella", "Pepperoni")),
        Pizza("Prosciutto", 10.0, R.drawable.pizza8, listOf("Tomate", "Mozzarella", "Jambon")),
        Pizza("Frutti di Mare", 13.0, R.drawable.pizza9, listOf("Tomate", "Mozzarella", "Fruits de mer"))
    )

    private val _cartItems = MutableStateFlow<List<OrderItem>>(emptyList()) // État du panier
    val cartItems: StateFlow<List<OrderItem>> = _cartItems.asStateFlow() // État pour observer le panier via un StateFlow

    // Variable qui récupère toutes les commandes de la bdd
    val orderHistory: Flow<List<Order>> = orderDao.getAllOrders()

    /**
     * Fonction pour charger toutes les pizzas disponibles.
     */
    fun loadPizzas(): List<Pizza> = allPizzas

    /**
     * Fonction pour charger une pizza en fonction de son id
     */
    fun loadPizza(id: Int): Pizza? = allPizzas.getOrNull(id)

    /**
     * Fonction pour ajouter une pizza au panier.
     */
    fun addToCart(pizza: Pizza, extraCheese: Int = 0) {
        Log.i(TAG, "Ajout de la pizza '${pizza.name}' au panier avec supplément de fromage : $extraCheese")
        val updatedCart = _cartItems.value.toMutableList()
        updatedCart.add(OrderItem(pizza = pizza, quantity = 1, extraCheese = extraCheese, id = nextId++))
        _cartItems.value = updatedCart // Mise à jour du StateFlow
    }

    /**
     * Fonction pour mettre à jour la quantité de fromage d'une pizza dans le panier.
     */
    fun updateCheeseQuantity(id: Int, newCheeseQuantity: Int) {
        Log.d(TAG, "Mise à jour de la quantité de fromage pour la pizza avec l'id : $id à $newCheeseQuantity")
        val updatedCart = _cartItems.value.toMutableList()
        val index = updatedCart.indexOfFirst { it.id == id }
        if (index != -1) {
            val item = updatedCart[index]
            updatedCart[index] = item.copy(extraCheese = newCheeseQuantity)
            _cartItems.value = updatedCart // Mise à jour du StateFlow
        }
    }

    /**
     * Fonction pour supprimer une pizza du panier.
     */
    fun removePizzaFromCart(id: Int) {
        Log.i(TAG, "Suppression de la pizza avec l'id : $id du panier")
        val updatedCart = _cartItems.value.toMutableList()
        updatedCart.removeIf { it.id == id }
        _cartItems.value = updatedCart // Mise à jour du StateFlow
    }

    /**
     * Fonction pour vider le panier.
     */
    fun clearCart() {
        Log.i(TAG, "Vidage du panier")
        _cartItems.value = emptyList() // Mise à jour du StateFlow
    }

    /**
     * Fonction pour ajouter une commande à la base de données Room.
     */
    fun addOrder(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                orderDao.insertOrder(order) // Insérer la commande dans Room
                Log.d(TAG, "Commande sauvegardée dans Room : $order")
            } catch (e: Exception) {
                Log.e(TAG, "Erreur lors de la sauvegarde de la commande : ${e.message}")
            }
        }
    }

    fun clearAllOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                orderDao.clearOrders() // Supprimer toutes les commandes de Room
                Log.d("PizzaApp-Log", "Toutes les commandes ont été supprimées")
            } catch (e: Exception) {
                Log.e("PizzaApp-Log", "Erreur lors de la suppression des commandes : ${e.message}")
            }
        }
    }

}

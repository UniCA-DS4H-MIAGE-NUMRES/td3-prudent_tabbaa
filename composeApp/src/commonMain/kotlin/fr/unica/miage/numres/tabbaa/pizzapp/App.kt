package fr.unica.miage.tabbaa.pizzapp

import androidx.compose.runtime.*
import fr.unica.miage.numres.tabbaa.pizzapp.screens.CaddyScreen
import fr.unica.miage.numres.tabbaa.pizzapp.data.DataSourceFactory
import fr.unica.miage.numres.tabbaa.pizzapp.model.OrderItem
import fr.unica.miage.numres.tabbaa.pizzapp.model.Pizza
import fr.unica.miage.numres.tabbaa.pizzapp.navigation.rememberNavControllerWrapper
import fr.unica.miage.numres.tabbaa.pizzapp.screens.CommandeHistoryScreen
import fr.unica.miage.numres.tabbaa.pizzapp.screens.HomeScreen
import fr.unica.miage.numres.tabbaa.pizzapp.screens.MenuScreen
import fr.unica.miage.numres.tabbaa.pizzapp.screens.PaymentScreen
import fr.unica.miage.numres.tabbaa.pizzapp.screens.PizzaScreen
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun App() {
    val navController = rememberNavControllerWrapper()
    val dataSource = remember {
        println("DEBUG: Initialisation de DataSource")
        DataSourceFactory.getInstance()
    }
    val pizzas = dataSource.getPizzas()

    var currentScreen by remember { mutableStateOf("HomeScreen") }
    var selectedPizza by remember { mutableStateOf<Pizza?>(null) }

    val cartItems = remember { MutableStateFlow<List<OrderItem>>(emptyList()) }
    val orderRepository = remember { DataSourceFactory.getOrderRepository() }

    fun addToCart(pizza: Pizza, extraCheese: Int) {
        val updatedCart = cartItems.value.toMutableList()
        val newItem = OrderItem(id = updatedCart.size + 1, pizza = pizza, quantity = 1, extraCheese = extraCheese)
        updatedCart.add(newItem)
        cartItems.value = updatedCart
    }

    navController.onNavigate = { route ->
        println("DEBUG: Changement d'écran vers $route")
        if (route.startsWith("PizzaScreen/")) {
            val pizzaId = route.substringAfter("PizzaScreen/").toIntOrNull()
            selectedPizza = pizzas.find { it.id == pizzaId }
            if (selectedPizza != null) {
                currentScreen = "PizzaScreen"
            }
        } else {
            currentScreen = route
        }
    }

    when (currentScreen) {
        "HomeScreen" -> HomeScreen(navController)
        "MenuScreen" -> MenuScreen(navController)
        "PizzaScreen" -> selectedPizza?.let { pizza ->
            PizzaScreen(
                pizza = pizza,
                navController = navController,
                onAddToCart = { pizza, extraCheese -> addToCart(pizza, extraCheese) }
            )
        }
        "CaddyScreen" -> CaddyScreen(
            cartItems = cartItems,
            onUpdateQuantity = { id, newCheese ->
                cartItems.value = cartItems.value.map {
                    if (it.id == id) it.copy(extraCheese = newCheese) else it
                }
            },
            onRemoveItem = { id ->
                cartItems.value = cartItems.value.filter { it.id != id }
            },
            onAddDuplicate = { pizza, extraCheese -> addToCart(pizza, extraCheese) },
            onCheckout = { /* À implémenter */ },
            navController = navController
        )
        "PaymentScreen" -> PaymentScreen(
            navController = navController,
            cartItems = cartItems,
            onClearCart = { cartItems.value = emptyList() },
            onAddOrder = { paymentMethod ->
                println("Commande passée avec la méthode de paiement : $paymentMethod")
            },
            orderRepository = orderRepository
        )
        "CommandeHistoryScreen" -> CommandeHistoryScreen(navController, orderRepository)
    }
}
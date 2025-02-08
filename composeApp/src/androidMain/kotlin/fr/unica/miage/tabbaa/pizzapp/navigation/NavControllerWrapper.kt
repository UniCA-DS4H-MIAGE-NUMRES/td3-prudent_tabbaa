package fr.unica.miage.tabbaa.pizzapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.unica.miage.tabbaa.pizzapp.screens.*
import fr.unica.miage.tabbaa.pizzapp.data.DataSourceFactory
import fr.unica.miage.tabbaa.pizzapp.model.Order
import fr.unica.miage.tabbaa.pizzapp.model.OrderItem
import fr.unica.miage.tabbaa.pizzapp.model.Pizza
import fr.unica.miage.tabbaa.pizzapp.utils.getCurrentDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

actual class NavControllerWrapper(val navController: NavHostController) {
    actual var onNavigate: ((String) -> Unit)? = null

    fun getCurrentRoute(): String? {
        return navController.currentBackStackEntry?.destination?.route
    }

    actual fun navigate(route: String) {
        println("DEBUG: Navigation vers $route")

        if (getCurrentRoute() == route) {
            println("DEBUG: Déjà sur la page $route, pas de navigation")
            return
        }

        onNavigate?.invoke(route)
        navController.navigate(route)
    }
}

@Composable
actual fun rememberNavControllerWrapper(): NavControllerWrapper {
    val navController = rememberNavController()
    val navWrapper = NavControllerWrapper(navController)

    val dataSource = DataSourceFactory.getInstance()
    val pizzas = dataSource.getPizzas()

    val cartItems = remember { MutableStateFlow<List<OrderItem>>(emptyList()) }
    val orderRepository = remember { DataSourceFactory.getOrderRepository() }
    val coroutineScope = rememberCoroutineScope()

    fun addToCart(pizza: Pizza, extraCheese: Int) {
        val updatedCart = cartItems.value.toMutableList()
        val newItem = OrderItem(id = updatedCart.size + 1, pizza = pizza, quantity = 1, extraCheese = extraCheese)
        updatedCart.add(newItem)
        cartItems.value = updatedCart
    }

    NavHost(navController = navController, startDestination = "HomeScreen") {
        composable("HomeScreen") { HomeScreen(navWrapper) }
        composable("MenuScreen") { MenuScreen(navWrapper) }
        composable("PizzaScreen/{pizzaId}") { backStackEntry ->
            val pizzaId = backStackEntry.arguments?.getString("pizzaId")?.toIntOrNull()
            val selectedPizza = pizzas.find { it.id == pizzaId }
            selectedPizza?.let {
                PizzaScreen(it, navWrapper, onAddToCart = { pizza, extraCheese -> addToCart(pizza, extraCheese) })
            }
        }
        composable("CaddyScreen") {
            CaddyScreen(
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
                navController = navWrapper
            )
        }
        composable("PaymentScreen") {
            PaymentScreen(
                navController = navWrapper,
                cartItems = cartItems,
                onClearCart = { cartItems.value = emptyList() },
                onAddOrder = { paymentMethod ->
                    coroutineScope.launch {
                        val order = Order(
                            date = getCurrentDate(), // Fonction pour récupérer la date du jour
                            totalPrice = cartItems.value.sumOf { item ->
                                (item.pizza.price * item.quantity) + (item.extraCheese * 0.02)
                            },
                            paymentMethod = paymentMethod,
                            items = cartItems.value
                        )
                        orderRepository.addOrder(order)
                        println("Commande ajoutée avec succès")
                    }
                },
                orderRepository = orderRepository
            )
        }
        composable("CommandeHistoryScreen") {
            CommandeHistoryScreen(navWrapper, orderRepository)
        }

    }

    return navWrapper
}

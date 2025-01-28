package fr.unica.miage.tabbaa.pizzapp

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.unica.miage.tabbaa.pizzapp.data.DataSource
import fr.unica.miage.tabbaa.pizzapp.data.DataSourceFactory
import fr.unica.miage.tabbaa.pizzapp.data.PizzaDatabase
import fr.unica.miage.tabbaa.pizzapp.model.Order
import fr.unica.miage.tabbaa.pizzapp.screens.CaddyScreen
import fr.unica.miage.tabbaa.pizzapp.screens.CommandeHistoryScreen
import fr.unica.miage.tabbaa.pizzapp.screens.DetailPizza
import fr.unica.miage.tabbaa.pizzapp.screens.HomeScreen
import fr.unica.miage.tabbaa.pizzapp.screens.PaymentScreen
import fr.unica.miage.tabbaa.pizzapp.screens.PizzaMenu
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@kotlinx.serialization.Serializable
object PizzaList // Objet pour la liste des pizzas

@kotlinx.serialization.Serializable
data class PizzaRoute(val idPizza: Int) // Classe pour la route d'une pizza

@Preview
@Composable
fun MyApp() {
    val navController = rememberNavController() // Contrôleur de navigation

    // Initialisation de la base de données Room
    val context = LocalContext.current
    val database = PizzaDatabase.getDatabase(context) // On appelle la méthode getDatabase pour obtenir une instance de la base de données
    val orderDao = database.orderDao() // On récupère le DAO

    /**
     * ViewModel pour la gestion des données
     */
    val viewModel: DataSource = viewModel(
        factory = DataSourceFactory(orderDao) // On passe le DAO à la factory
    )

    NavHost(
        navController = navController,
        startDestination = "HomeScreen" // Écran de démarrage
    ) {
        composable("HomeScreen") {
            HomeScreen(navController = navController) // Composable associé à HomeScreen
        }

        composable("MenuScreen") {
            PizzaMenu(
                menu = viewModel.loadPizzas(),
                navController = navController
            )
        }

        // Écran de l'historique des commandes
        composable("CommandeHistoryScreen") {
            CommandeHistoryScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        // Écran de paiement
        composable("PaymentScreen") {
            val cartItems by viewModel.cartItems.collectAsState()

            // Calcul du prix total
            val totalPrice = cartItems.sumOf { item ->
                (item.pizza.price * item.quantity) + (item.extraCheese * 0.02) // 0.02€ par tranche de fromage
            }

            PaymentScreen(
                navController = navController,
                totalPrice = totalPrice,
                onClearCart = { viewModel.clearCart() }, // Vide le panier après paiement
                onAddOrder = { paymentMethod ->
                    // Création d'une commande et sauvegarde dans Room
                    val order = Order(
                        date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date()),
                        totalPrice = totalPrice,
                        paymentMethod = paymentMethod,
                        items = cartItems
                    )
                    viewModel.addOrder(order) // Sauvegarde dans la BDD
                }
            )
        }

        // Écran d'une pizza
        composable("PizzaScreen/{pizzaId}") { backStackEntry ->
            val pizzaId = backStackEntry.arguments?.getString("pizzaId")?.toIntOrNull()
            val pizza = pizzaId?.let { viewModel.loadPizza(it) }
            if (pizza != null) {
                DetailPizza(
                    pizza = pizza,
                    ingredients = pizza.ingredients,
                    modifier = Modifier.fillMaxSize(),
                    onAddToCart = { extraCheese ->
                        viewModel.addToCart(pizza, extraCheese)
                    },
                    onNavigateToCaddy = {
                        navController.navigate("CaddyScreen") // Aller à l'écran du panier
                    },
                    onNavigateToMenu = {
                        navController.navigate("MenuScreen") // Retourner au menu
                    },
                    onNavigateBack = {
                        navController.popBackStack() // Retourner à l'écran précédent
                    },
                    navController = navController
                )
            }
        }

        // Écran du panier
        composable("CaddyScreen") {
            CaddyScreen(
                cartItems = viewModel.cartItems,
                onUpdateQuantity = { itemId, newQuantity ->
                    viewModel.updateCheeseQuantity(itemId, newQuantity) // Mettre à jour le fromage
                },
                onRemoveItem = { itemId ->
                    viewModel.removePizzaFromCart(itemId) // Supprimer une pizza
                },
                onAddDuplicate = { pizza, extraCheese ->
                    viewModel.addToCart(pizza, extraCheese) // Ajouter un doublon
                },
                onCheckout = {
                    navController.navigate("PaymentScreen") // Aller à l'écran de paiement
                },
                onNavigateBack = {
                    navController.popBackStack() // Retourner à l'écran précédent
                },
                navController = navController
            )
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    NavigationBar(
        containerColor = Color(0xFFE3B58A),
    ) {
        val TAG = "PizzaApp-Log"
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        Log.d(TAG, "Current route: $currentRoute")

        // NavigationBarItem pour HomeScreen
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Accueil",
                    tint = if (currentRoute == "HomeScreen") Color(0xFFE63946) else Color.Black
                )
            },
            label = {
                Text(
                    text = "Accueil",
                    color = if (currentRoute == "HomeScreen") Color(0xFFE63946) else Color.Black
                )
            },
            selected = currentRoute == "HomeScreen",
            onClick = {
                Log.d(TAG, "Click sur le bouton HomeScreen")
                if (currentRoute != "HomeScreen") {
                    navController.navigate("HomeScreen") {
                        popUpTo("HomeScreen") { inclusive = false } // Retour à HomeScreen sans supprimer HomeScreen
                        launchSingleTop = true // Évite de recréer plusieurs instances
                    }
                }
            }
        )

        // NavigationBarItem pour CaddyScreen
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = "Panier",
                    tint = if (currentRoute == "CaddyScreen") Color(0xFFE63946) else Color.Black
                )
            },
            label = {
                Text(
                    text = "Panier",
                    color = if (currentRoute == "CaddyScreen") Color(0xFFE63946) else Color.Black
                )
            },
            selected = currentRoute == "CaddyScreen",
            onClick = {
                Log.d(TAG, "Click sur le bouton CaddyScreen")
                if (currentRoute != "CaddyScreen") {
                    navController.navigate("CaddyScreen") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true } // Retour au début de la pile
                        launchSingleTop = true // Évite de recréer plusieurs instances
                        restoreState = true // Restaure l'état de la page si elle existe
                    }
                }
            }
        )

        // NavigationBarItem pour CommandeHistoryScreen
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Historique",
                    tint = if (currentRoute == "CommandeHistoryScreen") Color(0xFFE63946) else Color.Black
                )
            },
            label = {
                Text(
                    text = "Historique",
                    color = if (currentRoute == "CommandeHistoryScreen") Color(0xFFE63946) else Color.Black
                )
            },
            selected = currentRoute == "CommandeHistoryScreen",
            onClick = {
                Log.d(TAG, "Click sur le bouton CommandeHistoryScreen")
                if (currentRoute != "CommandeHistoryScreen") {
                    navController.navigate("CommandeHistoryScreen") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true } // Retour au début de la pile
                        launchSingleTop = true // Évite de recréer plusieurs instances
                        restoreState = true // Restaure l'état de la page si elle existe
                    }
                }
            }
        )
    }
}

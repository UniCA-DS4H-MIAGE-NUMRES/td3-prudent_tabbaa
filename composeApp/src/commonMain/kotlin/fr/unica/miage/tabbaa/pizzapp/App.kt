package fr.unica.miage.tabbaa.pizzapp

import androidx.compose.runtime.*
import fr.unica.miage.tabbaa.pizzapp.data.DataSourceFactory
import fr.unica.miage.tabbaa.pizzapp.model.Pizza
import fr.unica.miage.tabbaa.pizzapp.navigation.NavControllerWrapper
import fr.unica.miage.tabbaa.pizzapp.navigation.rememberNavControllerWrapper
import fr.unica.miage.tabbaa.pizzapp.screens.HomeScreen
import fr.unica.miage.tabbaa.pizzapp.screens.MenuScreen
import fr.unica.miage.tabbaa.pizzapp.screens.PizzaScreen
import fr.unica.miage.tabbaa.pizzapp.utils.PlatformConfig

@Composable
fun App() {
    val navController = rememberNavControllerWrapper()

    // Le `NavHost` gère la navigation dans Android
    if (PlatformConfig.isAndroid) {
        // `rememberNavControllerWrapper` contient le NavHost
        return
    }

    // Autres plateformes (Desktop/Web)
    val dataSource = remember { DataSourceFactory.getInstance() }
    val pizzas = dataSource.getPizzas()

    var currentScreen by remember { mutableStateOf("HomeScreen") }
    var selectedPizza by remember { mutableStateOf<Pizza?>(null) }

    navController.onNavigate = { route ->
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
            PizzaScreen(pizza = pizza, navController = navController)
        }
    }
}

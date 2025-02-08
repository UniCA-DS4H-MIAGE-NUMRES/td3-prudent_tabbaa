package fr.unica.miage.tabbaa.pizzapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.unica.miage.tabbaa.pizzapp.screens.HomeScreen
import fr.unica.miage.tabbaa.pizzapp.screens.MenuScreen
import fr.unica.miage.tabbaa.pizzapp.screens.PizzaScreen
import fr.unica.miage.tabbaa.pizzapp.data.DataSourceFactory

actual class NavControllerWrapper(val navController: NavHostController) {
    actual fun navigate(route: String) {
        navController.navigate(route)
    }

    actual var onNavigate: ((String) -> Unit)? = null
}

@Composable
actual fun rememberNavControllerWrapper(): NavControllerWrapper {
    val navController = rememberNavController()
    val navWrapper = NavControllerWrapper(navController)

    // Définir les routes pour Android
    val dataSource = DataSourceFactory.getInstance()
    val pizzas = dataSource.getPizzas()

    NavHost(navController = navController, startDestination = "HomeScreen") {
        composable("HomeScreen") { HomeScreen(navWrapper) }
        composable("MenuScreen") { MenuScreen(navWrapper) }
        composable("PizzaScreen/{pizzaId}") { backStackEntry ->
            val pizzaId = backStackEntry.arguments?.getString("pizzaId")?.toIntOrNull()
            val selectedPizza = pizzas.find { it.id == pizzaId }
            selectedPizza?.let { PizzaScreen(it, navWrapper) }
        }
    }

    return navWrapper
}

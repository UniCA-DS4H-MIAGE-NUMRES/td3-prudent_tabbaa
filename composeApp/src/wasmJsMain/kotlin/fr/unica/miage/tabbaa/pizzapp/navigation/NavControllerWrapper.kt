package fr.unica.miage.tabbaa.pizzapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

/**
 * Implémentation Web de la navigation.
 */
actual class NavControllerWrapper(private val navController: NavHostController) {
    actual fun navigate(route: String) {
        navController.navigate(route)
    }
}

@Composable
actual fun rememberNavControllerWrapper(): NavControllerWrapper {
    val navController = rememberNavController()
    return NavControllerWrapper(navController)
}

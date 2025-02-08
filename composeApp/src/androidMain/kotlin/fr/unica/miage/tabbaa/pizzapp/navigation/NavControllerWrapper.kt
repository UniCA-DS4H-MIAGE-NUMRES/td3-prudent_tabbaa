package fr.unica.miage.tabbaa.pizzapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

actual class NavControllerWrapper(val navController: NavHostController) {
    actual fun navigate(route: String) {
        navController.navigate(route)
    }

    actual var onNavigate: ((String) -> Unit)? = null
}

@Composable
actual fun rememberNavControllerWrapper(): NavControllerWrapper {
    val navController = rememberNavController()
    return NavControllerWrapper(navController)
}

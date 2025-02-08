package fr.unica.miage.tabbaa.pizzapp.navigation

import androidx.compose.runtime.Composable

/**
 * Implémentation Desktop de la navigation (pas de navigation réelle, simulateur).
 */
actual class NavControllerWrapper {
    actual fun navigate(route: String) {
        println("Navigation vers : $route (simulation Desktop)")
    }
}

@Composable
actual fun rememberNavControllerWrapper(): NavControllerWrapper {
    return NavControllerWrapper()
}

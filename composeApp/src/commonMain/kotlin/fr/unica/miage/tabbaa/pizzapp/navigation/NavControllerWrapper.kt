package fr.unica.miage.tabbaa.pizzapp.navigation

import androidx.compose.runtime.Composable

/**
 * Abstraction multiplateforme pour la navigation.
 */
expect class NavControllerWrapper {
    fun navigate(route: String)
}

@Composable
expect fun rememberNavControllerWrapper(): NavControllerWrapper

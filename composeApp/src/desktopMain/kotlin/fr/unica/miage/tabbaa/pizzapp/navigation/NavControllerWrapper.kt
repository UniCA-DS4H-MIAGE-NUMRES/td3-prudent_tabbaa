package fr.unica.miage.tabbaa.pizzapp.navigation

import androidx.compose.runtime.Composable

actual class NavControllerWrapper {
    actual var onNavigate: ((String) -> Unit)? = null

    actual fun navigate(route: String) {
        println("Navigation vers : $route (simulation Desktop)")
        onNavigate?.invoke(route)
    }
}

@Composable
actual fun rememberNavControllerWrapper(): NavControllerWrapper {
    return NavControllerWrapper()
}

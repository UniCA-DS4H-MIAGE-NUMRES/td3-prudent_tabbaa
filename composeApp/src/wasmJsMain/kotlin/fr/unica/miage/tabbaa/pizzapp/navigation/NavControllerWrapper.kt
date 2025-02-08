package fr.unica.miage.tabbaa.pizzapp.navigation

import androidx.compose.runtime.Composable

actual class NavControllerWrapper {
    actual fun navigate(route: String) {
        onNavigate?.invoke(route)
    }

    actual var onNavigate: ((String) -> Unit)? = null
}

@Composable
actual fun rememberNavControllerWrapper(): NavControllerWrapper {
    return NavControllerWrapper()
}

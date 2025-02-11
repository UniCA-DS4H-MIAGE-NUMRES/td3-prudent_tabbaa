package fr.unica.miage.numres.tabbaa.pizzapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

actual class NavControllerWrapper {
    actual var onNavigate: ((String) -> Unit)? = null

    actual fun navigate(route: String) {
        onNavigate?.invoke(route)
    }
}

@Composable
actual fun rememberNavControllerWrapper(): NavControllerWrapper {
    return remember { NavControllerWrapper() }
}
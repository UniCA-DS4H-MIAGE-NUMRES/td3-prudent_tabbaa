package fr.unica.miage.tabbaa.pizzapp.navigation

import androidx.compose.runtime.Composable

actual class NavControllerWrapper {
    actual fun navigate(route: String) {
        println("🚀 Navigating to: $route") // ✅ Ensure this prints when navigating
        if (onNavigate == null) {
            println("⚠️ Error: `onNavigate` is null! Navigation will fail.")
        }
        onNavigate?.invoke(route)
    }

    actual var onNavigate: ((String) -> Unit)? = { route ->
        println("✅ Default navigation to: $route")
    }
}

@Composable
actual fun rememberNavControllerWrapper(): NavControllerWrapper {
    return NavControllerWrapper()
}

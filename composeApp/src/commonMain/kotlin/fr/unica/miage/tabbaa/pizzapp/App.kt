package fr.unica.miage.tabbaa.pizzapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import fr.unica.miage.tabbaa.pizzapp.navigation.NavControllerWrapper
import fr.unica.miage.tabbaa.pizzapp.navigation.rememberNavControllerWrapper
import fr.unica.miage.tabbaa.pizzapp.screens.HomeScreen
import fr.unica.miage.tabbaa.pizzapp.screens.MenuScreen
import fr.unica.miage.tabbaa.pizzapp.utils.PlatformConfig

@Composable
fun App() {
    val navController = rememberNavControllerWrapper()

    var currentScreen by remember { mutableStateOf("HomeScreen") }

    navController.onNavigate = { route ->
        currentScreen = route
    }

    when (currentScreen) {
        "HomeScreen" -> HomeScreen(navController)
        "MenuScreen" -> MenuScreen(navController)
    }
}

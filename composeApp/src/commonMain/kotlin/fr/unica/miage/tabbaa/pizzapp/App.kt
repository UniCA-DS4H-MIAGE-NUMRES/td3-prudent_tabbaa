package fr.unica.miage.tabbaa.pizzapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.unica.miage.tabbaa.pizzapp.navigation.rememberNavControllerWrapper
import fr.unica.miage.tabbaa.pizzapp.screens.HomeScreen
import fr.unica.miage.tabbaa.pizzapp.screens.MenuScreen

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavControllerWrapper()

        Scaffold(
            topBar = {
                TopAppBar(title = { Text("PizzaApp") })
            },
            content = { padding ->
                Column(modifier = Modifier.padding(padding)) {
                    HomeScreen(navController)
                    MenuScreen(navController)
                }
            }
        )
    }
}

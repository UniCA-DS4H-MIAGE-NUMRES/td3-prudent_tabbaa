package fr.unica.miage.tabbaa.pizzapp.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import fr.unica.miage.tabbaa.pizzapp.navigation.NavControllerWrapper
import fr.unica.miage.tabbaa.pizzapp.utils.PlatformConfig

@Composable
fun BottomNavBar(navController: NavControllerWrapper) {
    NavigationBar(
        containerColor = Color(0xFFE3B58A)
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Accueil") },
            label = { Text("Accueil") },
            selected = false,
            onClick = { navController.navigate("HomeScreen") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Menu, contentDescription = "Menu") },
            label = { Text("Menu") },
            selected = false,
            onClick = { navController.navigate("MenuScreen") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Panier") },
            label = { Text("Panier") },
            selected = false,
            onClick = { navController.navigate("CaddyScreen") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Face, contentDescription = "Historique") },
            label = {
                Text("Historique", fontSize = PlatformConfig.textSize.sp)
                    },
            selected = false,
            onClick = { navController.navigate("CommandeHistoryScreen") }
        )
    }
}
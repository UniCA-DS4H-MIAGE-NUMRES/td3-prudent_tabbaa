package fr.unica.miage.numres.tabbaa.pizzapp.screens

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import fr.unica.miage.numres.tabbaa.pizzapp.navigation.NavControllerWrapper
import fr.unica.miage.numres.tabbaa.pizzapp.utils.PlatformConfig

@Composable
fun BottomNavBar(navController: NavControllerWrapper) {
    BottomNavigation(
        backgroundColor = Color(0xFFE3B58A)
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Accueil") },
            label = {
                Text(
                    "Accueil",
                    fontSize = PlatformConfig.bottomTextSiza.sp
                )
            },
            selected = false,
            onClick = { navController.navigate("HomeScreen") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Menu, contentDescription = "Menu") },
            label = {
                Text(
                    "Menu",
                    fontSize = PlatformConfig.bottomTextSiza.sp
                )
            },
            selected = false,
            onClick = { navController.navigate("MenuScreen") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Panier") },
            label = {
                Text(
                    "Panier",
                    fontSize = PlatformConfig.bottomTextSiza.sp
                )
            },
            selected = false,
            onClick = { navController.navigate("CaddyScreen") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Face, contentDescription = "Historique") },
            label = {
                Text(
                    "Historique",
                    fontSize = PlatformConfig.bottomTextSiza.sp
                )
            },
            selected = false,
            onClick = { navController.navigate("CommandeHistoryScreen") }
        )
    }
}
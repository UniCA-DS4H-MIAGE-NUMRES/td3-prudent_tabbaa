package fr.unica.miage.tabbaa.pizzapp.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.unica.miage.tabbaa.pizzapp.R

/**
 * Écran d'accueil
 */
@Composable
fun HomeScreen(navController: NavController) {
    val TAG = "PizzaApp-Log"
    Scaffold(
        containerColor = Color(0xFFFFF8E1),
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image( // Logo
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "PizzaApp Logo",
                    modifier = Modifier.size(240.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text( // Titre
                    text = "PizzaApp",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text( // Description
                    text = "Les meilleures pizzas italiennes livrées chez vous !",
                    fontSize = 18.sp,
                    color = Color(0xFF8D6E63),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                GradientButton( // Bouton pour voir le menu
                    text = "Voir le menu",
                    gradient = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFE63946), Color(0xFFF4A261))
                    ),
                    onClick = {
                        Log.d(TAG, "Navigation vers menu")
                        navController.navigate("MenuScreen")
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                GradientButton( // Bouton pour voir le panier
                    text = "Voir le panier",
                    gradient = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF2A9D8F), Color(0xFF1E8560))
                    ),
                    onClick = {
                        Log.d(TAG, "Navigation vers panier")
                        navController.navigate("CaddyScreen")
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                GradientButton( // Bouton pour voir l'historique des commandes
                    text = "Voir l'historique des commandes",
                    gradient = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFE3B58A), Color(0xFF8D6E63)
                        )
                    ),
                    onClick = {
                        Log.d(TAG, "Navigation vers historique")
                        navController.navigate("CommandeHistoryScreen")
                    }
                )
            }
        }
    )
}

/**
 * Bouton avec dégradé
 */
@Composable
fun GradientButton(
    text: String,
    gradient: Brush,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(0.8f)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient, shape = MaterialTheme.shapes.medium),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

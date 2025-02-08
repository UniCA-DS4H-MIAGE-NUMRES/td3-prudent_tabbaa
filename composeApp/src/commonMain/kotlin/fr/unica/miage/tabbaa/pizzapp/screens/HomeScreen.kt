package fr.unica.miage.tabbaa.pizzapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.unica.miage.tabbaa.pizzapp.navigation.NavControllerWrapper
import org.jetbrains.compose.resources.painterResource
import androidx.compose.material3.Scaffold

@Composable
fun HomeScreen(navController: NavControllerWrapper) {
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
                /*Image(
                    painter = painterResource("R.drawable.logo"),
                    contentDescription = "PizzaApp Logo",
                    modifier = Modifier.size(240.dp)
                )*/

                Spacer(modifier = Modifier.height(24.dp))

                Text( // ✅ Titre
                    text = "PizzaApp",
                    fontSize = 32.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text( // ✅ Description
                    text = "Les meilleures pizzas italiennes livrées chez vous !",
                    fontSize = 18.sp,
                    color = Color(0xFF8D6E63),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                GradientButton(
                    text = "Voir le menu",
                    gradient = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFE63946), Color(0xFFF4A261))
                    ),
                    onClick = {
                        navController.navigate("MenuScreen")
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                GradientButton(
                    text = "Voir le panier",
                    gradient = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF2A9D8F), Color(0xFF1E8560))
                    ),
                    onClick = {
                        navController.navigate("CaddyScreen")
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                GradientButton(
                    text = "Voir l'historique des commandes",
                    gradient = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFE3B58A), Color(0xFF8D6E63))
                    ),
                    onClick = {
                        navController.navigate("CommandeHistoryScreen")
                    }
                )
            }
        }
    )
}

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
                color = Color.White
            )
        }
    }
}

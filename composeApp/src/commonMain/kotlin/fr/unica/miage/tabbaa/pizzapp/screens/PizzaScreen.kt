package fr.unica.miage.tabbaa.pizzapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.unica.miage.tabbaa.pizzapp.model.Pizza
import fr.unica.miage.tabbaa.pizzapp.navigation.NavControllerWrapper
import fr.unica.miage.tabbaa.pizzapp.utils.PlatformConfig
import fr.unica.miage.tabbaa.pizzapp.utils.loadImage
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PizzaScreen(
    pizza: Pizza,
    navController: NavControllerWrapper,
    onAddToCart: (Pizza, Int) -> Unit
) {
    var extraCheese by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = Color(0xFFFFF8E1),
        topBar = {
            TopAppBar(
                title = { Text("Détails de la Pizza", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("MenuScreen") }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFE3B58A))
            )
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    painter = loadImage(pizza.image),
                    contentDescription = pizza.name,
                    modifier = Modifier.size(180.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(pizza.name, fontSize = 28.sp, color = Color(0xFF1E8560))
                Text("Prix: ${round(pizza.price * 100) / 100}€", fontSize = 20.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(16.dp))

                Text("Ingrédients :", fontSize = 20.sp, color = Color(0xFFA0522D))
                Spacer(modifier = Modifier.height(8.dp))
                pizza.ingredients.forEach { ingredient ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .background(Color(0xFFE3B58A), shape = MaterialTheme.shapes.medium)
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.Check, contentDescription = null, tint = Color(0xFFE63946))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(ingredient, style = MaterialTheme.typography.bodyLarge)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Fromage supplémentaire : $extraCheese g", fontSize = 16.sp)
                Slider(
                    value = extraCheese.toFloat(),
                    onValueChange = { extraCheese = (round(it / 10) * 10).toInt() },
                    valueRange = 0f..100f,
                    steps = 9,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFFE63946),
                        activeTrackColor = Color(0xFFFFC107)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Bouton Ajouter au Panier (ajoute sans navigation)
                Button(
                    onClick = { onAddToCart(pizza, extraCheese) },
                    modifier = Modifier
                        .fillMaxWidth(PlatformConfig.buttonWidth)
                        .height(PlatformConfig.buttonHeight.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA0522D))
                ) {
                    Text("Ajouter au Panier", color = Color.White)
                }
            }
        }
    )
}

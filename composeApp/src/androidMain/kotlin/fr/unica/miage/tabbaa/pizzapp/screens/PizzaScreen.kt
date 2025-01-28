package fr.unica.miage.tabbaa.pizzapp.screens

import android.util.Log
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.unica.miage.tabbaa.pizzapp.BottomBar
import fr.unica.miage.tabbaa.pizzapp.model.Pizza

/**
 * Écran de détail de la pizza
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPizza(
    pizza: Pizza, // Pizza
    ingredients: List<String>, // Ingrédients
    modifier: Modifier = Modifier, // Modificateur
    onAddToCart: (extraCheese: Int) -> Unit, // Fonction pour ajouter au panier
    onNavigateToCaddy: () -> Unit, // Fonction pour naviguer vers le panier
    onNavigateToMenu: () -> Unit, // Fonction pour naviguer vers le menu
    onNavigateBack: () -> Unit, // Fonction pour naviguer en arrière
    navController: NavController // Contrôleur de navigation
) {
    var extraCheese by remember { mutableIntStateOf(0) } // Fromage supplémentaire
    val scrollState = rememberScrollState() // État du scroll (oblg car trop d'ingrédient parfois)

    Scaffold(
        containerColor = Color(0xFFFFF8E1),
        topBar = {
            TopAppBar( // Header
                title = {
                    Text(
                        text = "Détails de la Pizza",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = { // Bouton de retour
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE3B58A),
                    titleContentColor = Color(0xFFFFF8E1),
                    actionIconContentColor = Color(0xFFFFF8E1),
                )
            )
        },
        bottomBar = {
            BottomBar(navController = navController) // Ajout de la BottomBar
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
                // Image de la pizza
                Image(
                    painter = painterResource(id = pizza.image),
                    contentDescription = pizza.name,
                    modifier = Modifier.size(180.dp)
                )

                // Nom de la pizza
                Text(
                    text = pizza.name,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E8560)
                )

                // Prix
                Text(
                    text = "Prix: ${"%.2f".format(pizza.price)}€",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                // Liste des ingrédients
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = "Ingrédients :",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFA0522D)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    ingredients.forEach { ingredient ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .background(
                                    color = Color(0xFFE3B58A),
                                    shape = MaterialTheme.shapes.medium
                                )
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                tint = Color(0xFFE63946),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = ingredient,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black
                            )
                        }
                    }
                }

                // Fromage supplémentaire
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Fromage supplémentaire : $extraCheese g",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Slider(
                        value = extraCheese.toFloat(),
                        onValueChange = { newValue -> extraCheese = newValue.coerceAtMost(100f).toInt() },
                        valueRange = 0f..100f,
                        steps = 9,
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFFE63946),
                            activeTrackColor = Color(0xFFFFC107),
                            inactiveTrackColor = Color(0xFFA0522D)
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                // Bouton Ajouter au Panier
                Button(
                    onClick = {
                        Log.i("PizzaApp-Log", "Ajout de la pizza au panier avec $extraCheese g de fromage supplémentaire")
                        onAddToCart(extraCheese)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA0522D))
                ) {
                    Text("Ajouter au Panier", fontSize = 16.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    )
}

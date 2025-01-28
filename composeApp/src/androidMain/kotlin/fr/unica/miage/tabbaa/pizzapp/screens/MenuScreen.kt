package fr.unica.miage.tabbaa.pizzapp.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.unica.miage.tabbaa.pizzapp.BottomBar
import fr.unica.miage.tabbaa.pizzapp.model.Pizza

/**
 * Écran du menu
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PizzaMenu(
    menu: List<Pizza>,
    navController: NavController
) {
    val TAG = "PizzaApp-Log"
    Scaffold(
        containerColor = Color(0xFFFFF8E1),
        topBar = {
            TopAppBar( // Header
                title = {
                    Text(
                        "Menu des Pizzas",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                navigationIcon = { // Bouton de retour
                    IconButton(onClick = {
                        Log.i(TAG, "Navigation vers l'écran précédent")
                        navController.popBackStack()
                    }) {
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
            LazyVerticalGrid( // Grille pour afficher les pizzas
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(bottom = 0.dp)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(menu) { pizza -> // Liste des pizzas
                    Log.d(TAG, "Affichage de la carte de la pizza : ${pizza.name}")
                    PizzaCard(
                        pizza = pizza,
                        onClickPizza = { // Quand on clique sur une pizza
                            navController.navigate("PizzaScreen/${menu.indexOf(pizza)}")
                        },
                        modifier = Modifier
                            .size(width = 160.dp, height = 200.dp)
                    )
                }
            }
        }
    )
}

/**
 * Composant pour afficher une pizza
 */
@Composable
fun PizzaCard(
    pizza: Pizza,
    onClickPizza: () -> Unit,
    modifier: Modifier = Modifier
) {
    val TAG = "PizzaApp-Log"
    Card(
        modifier = modifier
            .clickable(onClick = { // Quand on clique sur une pizza
                Log.d(TAG, "Carte de la pizza cliquée : ${pizza.name}")
                onClickPizza()
            })
            .shadow(
                elevation = 20.dp,
                ambientColor = Color(0xFFE63946),
                shape = MaterialTheme.shapes.medium
            )
            .clip(MaterialTheme.shapes.medium),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF8E1)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly // Pour espacer les éléments equi
        ) {
            Image( // Image de la pizza
                painter = painterResource(id = pizza.image),
                contentDescription = pizza.name,
                modifier = Modifier
                    .size(80.dp)
            )
            Text( // Nom de la pizza
                text = pizza.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF1E8560),
                modifier = Modifier.padding(top = 8.dp)
            )
            Text( // Prix de la pizza
                text = "Prix: ${"%.2f".format(pizza.price)}€",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}
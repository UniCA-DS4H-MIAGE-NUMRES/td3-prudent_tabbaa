package fr.unica.miage.tabbaa.pizzapp.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import fr.unica.miage.tabbaa.pizzapp.BottomBar
import fr.unica.miage.tabbaa.pizzapp.data.DataSource
import fr.unica.miage.tabbaa.pizzapp.model.Order
import fr.unica.miage.tabbaa.pizzapp.model.Pizza

/**
 * Écran de l'historique des commandes
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommandeHistoryScreen(
    navController: NavController, // Contrôleur de navigation
    viewModel: DataSource // Liste des commandes
) {
    val TAG = "PizzaApp-Log"
    val orderHistory by viewModel.orderHistory.collectAsStateWithLifecycle(initialValue = emptyList()) // Historique des commandes

    Scaffold(
        containerColor = Color(0xFFFFF8E1),
        topBar = {
            TopAppBar( // Header
                title = {
                    Text(
                        text = "Historique des Commandes",
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
                actions = { // Bouton pour supprimer toutes les commandes
                    IconButton(onClick = {
                        Log.i(TAG, "Suppression de toutes les commandes")
                        viewModel.clearAllOrders() // Appel de la fonction pour supprimer les commandes
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Supprimer toutes les commandes",
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
            if (orderHistory.isEmpty()) { // Si l'historique est vide
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(bottom = 0.dp)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Aucune commande enregistrée pour le moment.",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFA0522D)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(bottom = 0.dp)
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(orderHistory) { order -> // Liste des commandes
                        OrderCard(order = order)
                    }
                }
            }
        }
    )
}

/**
 * Composant pour afficher une commande
 */
@Composable
fun OrderCard(order: Order) {
    var isExpanded by remember { mutableStateOf(false) } // Permet d'ouvrir ou de fermer la commande (pour voir les détails)

    Card( // Carte de la commande
        modifier = Modifier
            .shadow(
                elevation = 20.dp,
                ambientColor = Color(0xFFE63946),
                shape = MaterialTheme.shapes.medium
            )
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF8E1)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text( // Date de la commande
                        text = "Date : ${order.date}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E8560)
                    )
                    Text( // Prix total de la commande
                        text = "Total : ${"%.2f".format(order.totalPrice)}€",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                    Text( // Mode de paiement
                        text = "Paiement : ${order.paymentMethod}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFA0522D)
                    )
                }
                IconButton(onClick = { isExpanded = !isExpanded }) { // Bouton pour ouvrir/fermer la commande
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expand Order",
                        tint = Color(0xFFA0522D)
                    )
                }
            }

            if (isExpanded) { // Si la commande est ouverte alors on affiche les détails
                Spacer(modifier = Modifier.height(8.dp))
                order.items.forEach { (pizza, quantity, extraCheese) ->
                    OrderItem(pizza = pizza, quantity = quantity, extraCheese = extraCheese)
                }
            }
        }
    }
}

/**
 * Composant pour afficher un article dans une commande
 */
@SuppressLint("DefaultLocale")
@Composable
fun OrderItem(
    pizza: Pizza,
    quantity: Int,
    extraCheese: Int
) {
    // Coût du fromage supplémentaire
    val cheesePrice = extraCheese * 0.02
    // Prix total de la commande
    val totalPrice = pizza.price + cheesePrice

    Column(
        modifier = Modifier
            .shadow(
                elevation = 20.dp,
                ambientColor = Color(0xFFE63946),
                shape = MaterialTheme.shapes.medium
            )
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                color = Color(0xFFFFF8E1),
                shape = MaterialTheme.shapes.medium
            )
            .padding(8.dp)
    ) {
        Text( // Nom de la pizza
            text = pizza.name,
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF1E8560)
        )
        Text( // Quantité de la pizza
            text = if (extraCheese > 0)
                "Fromage supplémentaire : $extraCheese g (${String.format("%.2f", cheesePrice)}€)"
            else
                "Pas de fromage supplémentaire",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFA0522D)
        )
        Text( // Prix de la pizza
            text = "Prix : ${String.format("%.2f", totalPrice)}€",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
    }
}


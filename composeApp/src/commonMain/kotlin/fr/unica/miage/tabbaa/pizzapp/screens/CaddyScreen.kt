package fr.unica.miage.tabbaa.pizzapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.unit.dp
import fr.unica.miage.tabbaa.pizzapp.model.OrderItem
import fr.unica.miage.tabbaa.pizzapp.model.Pizza
import fr.unica.miage.tabbaa.pizzapp.navigation.NavControllerWrapper
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.round

/**
 * Écran du panier (adapté pour Kotlin Multiplatform)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaddyScreen(
    cartItems: StateFlow<List<OrderItem>>, // Liste des pizzas dans le panier
    onUpdateQuantity: (Int, Int) -> Unit, // Fonction pour mettre à jour la quantité d'une même pizza
    onRemoveItem: (Int) -> Unit, // Fonction pour supprimer un pizza du panier
    onAddDuplicate: (Pizza, Int) -> Unit, // Fonction pour ajouter un doublon d'une pizza
    onCheckout: () -> Unit, // Fonction pour passer la commande
    navController: NavControllerWrapper // Contrôleur de navigation KMP
) {
    val cartItemsState by cartItems.collectAsState()

    // Calcul du prix total
    val totalPrice by derivedStateOf {
        cartItemsState.sumOf { item ->
            (item.pizza.price * item.quantity) + (item.extraCheese * 0.02)
        }
    }

    Scaffold(
        containerColor = Color(0xFFFFF8E1),
        topBar = {
            TopAppBar( // Header
                title = { Text("Votre Panier", style = MaterialTheme.typography.titleLarge, color = Color.White) },
                navigationIcon = { // Bouton de retour
                    IconButton(onClick = { navController.navigate("MenuScreen") }) {
                        println("DEBUG: Navigation vers MenuScreen")
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFE3B58A))
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cartItemsState) { item ->
                    CartItem(
                        item = item,
                        onUpdateQuantity = { id, newCheese ->
                            println("DEBUG: Mise à jour du fromage pour pizza ID $id à $newCheese g")
                            if (newCheese in 0..100) onUpdateQuantity(id, newCheese)
                        },
                        onRemoveItem = {
                            onRemoveItem(item.id)
                            println("DEBUG: Suppression de la pizza ID ${item.id}")
                        },
                        onAddDuplicate = {
                            onAddDuplicate(item.pizza, item.extraCheese)
                            println("DEBUG: Ajout d'un doublon pour la pizza ${item.pizza.name}")
                        }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.Transparent, shape = MaterialTheme.shapes.medium)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Total: ${round(totalPrice * 100) / 100}€",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.End)
                )

                Button(
                    onClick = { navController.navigate("PaymentScreen") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA0522D))
                ) {
                    Text("Passer la commande", style = MaterialTheme.typography.titleLarge, color = Color.White)
                }
            }
        }
    }
}

/**
 * Composant pour afficher une pizza dans le panier
 */
@Composable
fun CartItem(
    item: OrderItem,
    onUpdateQuantity: (Int, Int) -> Unit,
    onRemoveItem: () -> Unit,
    onAddDuplicate: () -> Unit
) {
    Card(
        modifier = Modifier
            .shadow(20.dp)
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = item.pizza.name, style = MaterialTheme.typography.titleMedium, color = Color(0xFF1E8560))
                Text(
                    text = "Prix: ${round(item.pizza.price * 100) / 100}€",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                Text(
                    text = if (item.extraCheese > 0)
                        "Fromage supplémentaire: ${item.extraCheese} g (${round(item.extraCheese * 0.02 * 100) / 100}€)"
                    else "Pas de fromage supplémentaire",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFA0522D)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = {
                        if (item.extraCheese > 0) {
                            onUpdateQuantity(item.id, item.extraCheese - 10)
                        }
                    },
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color(0xFFE3B58A))
                ) {
                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Remove Cheese", tint = Color.White)
                }

                Text(text = item.extraCheese.toString(), style = MaterialTheme.typography.titleMedium, color = Color(0xFFA0522D))

                IconButton(
                    onClick = {
                        if (item.extraCheese + 10 <= 100) {
                            onUpdateQuantity(item.id, item.extraCheese + 10)
                        }
                    },
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color(0xFFE3B58A))
                ) {
                    Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "Add Cheese", tint = Color.White)
                }
            }

            IconButton(onClick = onAddDuplicate, colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color(0xFF1E8560))) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Same Pizza", tint = Color.White)
            }

            IconButton(onClick = onRemoveItem, colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color(0xFFE63946))) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove Item", tint = Color.White)
            }
        }
    }
}

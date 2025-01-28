package fr.unica.miage.tabbaa.pizzapp.screens

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.navigation.NavController
import fr.unica.miage.tabbaa.pizzapp.BottomBar
import fr.unica.miage.tabbaa.pizzapp.model.OrderItem
import fr.unica.miage.tabbaa.pizzapp.model.Pizza
import kotlinx.coroutines.flow.StateFlow

/**
 * Écran du panier
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun CaddyScreen(
    cartItems: StateFlow<List<OrderItem>>, // Liste des pizzas dans le panier
    onUpdateQuantity: (Int, Int) -> Unit, // Fonction pour mettre à jour la quantité d'une même pizza
    onRemoveItem: (Int) -> Unit, // Fonction pour supprimer un pizza du panier
    onAddDuplicate: (Pizza, Int) -> Unit, // Fonction pour ajouter un doublon d'une pizza
    onCheckout: () -> Unit, // Fonction pour passer la commande
    onNavigateBack: () -> Unit, // Fonction pour naviguer vers l'écran précédent
    navController: NavController // Contrôleur de navigation
) {
    val TAG = "PizzaApp-Log"

    // Collecte la liste des pizza
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
                    IconButton(onClick = onNavigateBack) {
                        Log.i(TAG, "Navigation vers l'écran précédent")
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors( // Couleurs du header
                    containerColor = Color(0xFFE3B58A)
                )
            )
        },
        bottomBar = {
            BottomBar(navController = navController) // Ajout de la BottomBar
        },
        content = { innerPadding -> // Contenu
            Column( 
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                LazyColumn( // Liste des pizzas
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(cartItemsState) { item -> // Pour chaque pizza
                        CartItem(
                            item = item,
                            onUpdateQuantity = { id, newCheese -> // Mettre à jour la quantité de fromage
                                Log.d(TAG, "Mise à jour du fromage pour la pizza avec l'id : $id à $newCheese g")
                                if (newCheese in 0..100) onUpdateQuantity(id, newCheese)
                            },
                            onRemoveItem = { // Supprimer la pizza
                                onRemoveItem(item.id)
                                Log.i(TAG, "Suppression de la pizza avec l'id : ${item.id}")
                            },
                            onAddDuplicate = { // Ajouter un doublon de la pizza
                                onAddDuplicate(item.pizza, item.extraCheese)
                                Log.i(TAG, "Ajout d'un doublon pour la pizza : ${item.pizza.name}")
                            }
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(
                            color = Color.Transparent,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text( // Prix total
                        text = "Total: ${"%.2f".format(totalPrice)}€",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.End)
                    )

                    Button( // Bouton pour valider le panier et passernà l'écran de paiemnt
                        onClick = onCheckout,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA0522D))
                    ) {
                        Text("Passer la commande", style = MaterialTheme.typography.titleLarge, color = Color.White)
                    }
                }
            }
        }
    )
}

/**
 * Composant pour afficher une pizza dans le panier
 * Gère tout ce qui concerne une et une seul pizza
 */
@SuppressLint("DefaultLocale")
@Composable
fun CartItem(
    item: OrderItem, // Pizza
    onUpdateQuantity: (Int, Int) -> Unit, // Fonction pour mettre à jour la quantité de fromage
    onRemoveItem: () -> Unit, // Fonction pour supprimer la pizza
    onAddDuplicate: () -> Unit // Fonction pour ajouter un doublon de la pizza
) {
    val TAG = "PizzaApp-Log"

    Card( // Carte de la pizza
        modifier = Modifier
            .shadow(20.dp)
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF8E1)
        )
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
                Text( // Nom de la pizza
                    text = item.pizza.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF1E8560)
                )
                Text( // Prix de la pizza
                    text = "Prix: ${"%.2f".format(item.pizza.price)}€",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                Text( // Quantité de fromage
                    text = if (item.extraCheese > 0)
                        "Fromage supplémentaire: ${item.extraCheese} g (${String.format("%.2f", item.extraCheese * 0.02)}€)"
                    else
                        "Pas de fromage supplémentaire",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFA0522D)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton( // Bouton pour réduire la quantité de fromage
                    onClick = {
                        Log.d(TAG, "Réduction du fromage pour la pizza avec l'id : ${item.id}")
                        if (item.extraCheese > 0) {
                            onUpdateQuantity(item.id, item.extraCheese - 10)
                        }
                    },
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color(0xFFE3B58A))
                ) {
                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Remove Cheese", tint = Color.White)
                }

                Text( // Quantité de fromage
                    text = item.extraCheese.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFFA0522D)
                )

                IconButton( // Bouton pour augmenter la quantité de fromage
                    onClick = {
                        Log.d(TAG, "Augmentation du fromage pour la pizza avec l'id : ${item.id}")
                        if (item.extraCheese + 10 <= 100) {
                            onUpdateQuantity(item.id, item.extraCheese + 10)
                        }
                    },
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color(0xFFE3B58A))
                ) {
                    Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "Add Cheese", tint = Color.White)
                }
            }

            IconButton( // Bouton pour ajouter le duoublon de la pizza
                onClick = onAddDuplicate,
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color(0xFF1E8560))
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Same Pizza", tint = Color.White)
            }

            IconButton( // Bouton pour supprimer la pizza
                onClick = onRemoveItem,
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color(0xFFE63946))
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove Item", tint = Color.White)
            }
        }
    }
}

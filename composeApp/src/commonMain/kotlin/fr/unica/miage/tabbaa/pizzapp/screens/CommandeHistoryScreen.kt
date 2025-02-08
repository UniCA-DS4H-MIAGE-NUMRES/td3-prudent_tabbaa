package fr.unica.miage.tabbaa.pizzapp.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fr.unica.miage.tabbaa.pizzapp.model.Order
import fr.unica.miage.tabbaa.pizzapp.model.Pizza
import fr.unica.miage.tabbaa.pizzapp.navigation.NavControllerWrapper
import fr.unica.miage.tabbaa.pizzapp.data.OrderRepository
import fr.unica.miage.tabbaa.pizzapp.model.OrderItem
import kotlinx.coroutines.launch
import kotlin.math.round

/**
 * Écran de l'historique des commandes (multiplateforme)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommandeHistoryScreen(
    navController: NavControllerWrapper,
    orderRepository: OrderRepository
) {
    val orderHistory = remember { mutableStateOf<List<Order>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    // Charger les commandes dès l'affichage
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            orderRepository.getOrders().collect { orders ->
                orderHistory.value = orders
            }
        }
    }

    Scaffold(
        containerColor = Color(0xFFFFF8E1),
        topBar = {
            TopAppBar(
                title = { Text("Historique des Commandes", style = MaterialTheme.typography.titleLarge, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("HomeScreen") }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            orderRepository.deleteAllOrders()
                            orderHistory.value = emptyList()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Supprimer toutes les commandes", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFE3B58A))
            )
        },
        content = { innerPadding ->
            if (orderHistory.value.isEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Aucune commande enregistrée.", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = Color(0xFFA0522D))
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(orderHistory.value) { order ->
                        OrderCard(order)
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
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFFFFF8E1)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Date : ${order.date}", fontWeight = FontWeight.Bold, color = Color(0xFF1E8560))
                    Text("Total : ${round(order.totalPrice * 100) / 100}€", color = Color.Black)
                    Text("Paiement : ${order.paymentMethod}", color = Color(0xFFA0522D))
                }
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expand Order",
                        tint = Color(0xFFA0522D)
                    )
                }
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                order.items.forEach { item ->
                    OrderItemView(item = item)
                }
            }

        }
    }
}

/**
 * Composant pour afficher un article dans une commande
 */
@Composable
fun OrderItem(
    pizza: Pizza,
    quantity: Int,
    extraCheese: Int
) {
    val cheesePrice = extraCheese * 0.02
    val totalPrice = pizza.price + cheesePrice

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFFFFF8E1))
            .padding(8.dp)
    ) {
        Text(pizza.name, fontWeight = FontWeight.Bold, color = Color(0xFF1E8560))
        Text("Quantité : $quantity", color = Color.Black)
        Text(
            if (extraCheese > 0) "Fromage supplémentaire : $extraCheese g (${round(cheesePrice * 100) / 100}€)"
            else "Pas de fromage supplémentaire",
            color = Color(0xFFA0522D)
        )
        Text("Prix total : ${round(totalPrice * 100) / 100}€", color = Color.Black)
    }
}

@Composable
fun OrderItemView(item: OrderItem) {
    val cheesePrice = item.extraCheese * 0.02
    val totalPrice = item.pizza.price + cheesePrice

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFFFFF8E1))
            .padding(8.dp)
    ) {
        Text(item.pizza.name, fontWeight = FontWeight.Bold, color = Color(0xFF1E8560))
        Text("Quantité : ${item.quantity}", color = Color.Black)
        Text(
            if (item.extraCheese > 0)
                "Fromage supplémentaire : ${item.extraCheese} g (${round(cheesePrice * 100) / 100}€)"
            else
                "Pas de fromage supplémentaire",
            color = Color(0xFFA0522D)
        )
        Text("Prix total : ${round(totalPrice * 100) / 100}€", color = Color.Black)
    }
}

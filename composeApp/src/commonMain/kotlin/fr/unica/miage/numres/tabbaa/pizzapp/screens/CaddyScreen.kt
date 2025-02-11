package fr.unica.miage.numres.tabbaa.pizzapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.unica.miage.numres.tabbaa.pizzapp.model.OrderItem
import fr.unica.miage.numres.tabbaa.pizzapp.model.Pizza
import fr.unica.miage.numres.tabbaa.pizzapp.navigation.NavControllerWrapper
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.round

@Composable
fun CaddyScreen(
    cartItems: StateFlow<List<OrderItem>>,
    onUpdateQuantity: (Int, Int) -> Unit,
    onRemoveItem: (Int) -> Unit,
    onAddDuplicate: (Pizza, Int) -> Unit,
    onCheckout: () -> Unit,
    navController: NavControllerWrapper
) {
    val cartItemsState by cartItems.collectAsState()
    val scaffoldState = rememberScaffoldState()

    val totalPrice by derivedStateOf {
        cartItemsState.sumOf { item ->
            (item.pizza.price * item.quantity) + (item.extraCheese * 0.02)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color(0xFFFFF8E1),
        topBar = {
            TopAppBar(
                title = { Text("Votre Panier", style = MaterialTheme.typography.h6, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("MenuScreen") }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour", tint = Color.White)
                    }
                },
                backgroundColor = Color(0xFFE3B58A)
            )
        },
        bottomBar = {
            BottomNavBar(navController = navController)
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
                        onUpdateQuantity = onUpdateQuantity,
                        onRemoveItem = { onRemoveItem(item.id) },
                        onAddDuplicate = { onAddDuplicate(item.pizza, item.extraCheese) }
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
                    style = MaterialTheme.typography.h5,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.End)
                )

                Button(
                    onClick = { navController.navigate("PaymentScreen") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA0522D))
                ) {
                    Text("Passer la commande", color = Color.White)
                }
            }
        }
    }
}

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
        backgroundColor = Color(0xFFFFF8E1)
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
                Text(text = item.pizza.name, style = MaterialTheme.typography.h6, color = Color(0xFF1E8560))
                Text(
                    text = "Prix: ${round(item.pizza.price * 100) / 100}€",
                    style = MaterialTheme.typography.body1,
                    color = Color.Black
                )
                Text(
                    text = if (item.extraCheese > 0)
                        "Fromage supplémentaire: ${item.extraCheese} g (${round(item.extraCheese * 0.02 * 100) / 100}€)"
                    else "Pas de fromage supplémentaire",
                    style = MaterialTheme.typography.body2,
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
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Remove Cheese",
                        tint = Color(0xFFE3B58A)
                    )
                }

                Text(
                    text = item.extraCheese.toString(),
                    style = MaterialTheme.typography.h6,
                    color = Color(0xFFA0522D)
                )

                IconButton(
                    onClick = {
                        if (item.extraCheese + 10 <= 100) {
                            onUpdateQuantity(item.id, item.extraCheese + 10)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Add Cheese",
                        tint = Color(0xFFE3B58A)
                    )
                }
            }

            IconButton(onClick = onAddDuplicate) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Same Pizza",
                    tint = Color(0xFF1E8560)
                )
            }

            IconButton(onClick = onRemoveItem) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove Item",
                    tint = Color(0xFFE63946)
                )
            }
        }
    }
}
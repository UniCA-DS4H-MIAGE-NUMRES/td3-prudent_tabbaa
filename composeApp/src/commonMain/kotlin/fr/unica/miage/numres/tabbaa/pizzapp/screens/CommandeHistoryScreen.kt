package fr.unica.miage.numres.tabbaa.pizzapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.unica.miage.numres.tabbaa.pizzapp.data.OrderRepository
import fr.unica.miage.numres.tabbaa.pizzapp.model.Order
import fr.unica.miage.numres.tabbaa.pizzapp.model.Pizza
import fr.unica.miage.numres.tabbaa.pizzapp.navigation.NavControllerWrapper
import fr.unica.miage.numres.tabbaa.pizzapp.model.OrderItem
import fr.unica.miage.numres.tabbaa.pizzapp.utils.PlatformConfig
import kotlinx.coroutines.launch
import kotlin.math.round

@Composable
fun CommandeHistoryScreen(
    navController: NavControllerWrapper,
    orderRepository: OrderRepository
) {
    val orderHistory = remember { mutableStateOf<List<Order>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            orderRepository.getOrders().collect { orders ->
                orderHistory.value = orders
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color(0xFFFFF8E1),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Historique des Commandes",
                        fontSize = PlatformConfig.titleSize.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("HomeScreen") },
                        modifier = Modifier.size(PlatformConfig.iconSize.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                orderRepository.deleteAllOrders()
                                orderHistory.value = emptyList()
                            }
                        },
                        modifier = Modifier.size(PlatformConfig.iconSize.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Supprimer toutes les commandes",
                            tint = Color.White
                        )
                    }
                },
                backgroundColor = Color(0xFFE3B58A)
            )
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        if (orderHistory.value.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Aucune commande enregistrée.",
                    fontSize = PlatformConfig.emptyMessageSize.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFA0522D)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(PlatformConfig.screenPadding.dp),
                verticalArrangement = Arrangement.spacedBy(PlatformConfig.itemSpacing.dp)
            ) {
                items(orderHistory.value) { order ->
                    OrderCard(order)
                }
            }
        }
    }
}

@Composable
fun OrderCard(order: Order) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PlatformConfig.cardPadding.dp)
            .background(Color(0xFFFFF8E1)),
        elevation = PlatformConfig.cardElevation.dp,
        backgroundColor = Color(0xFFFFF8E1)
    ) {
        Column(
            modifier = Modifier.padding(PlatformConfig.cardPadding.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Date : ${order.date}",
                        fontSize = PlatformConfig.orderDateSize.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E8560)
                    )
                    Text(
                        "Total : ${round(order.totalPrice * 100) / 100}€",
                        fontSize = PlatformConfig.orderTotalSize.sp,
                        color = Color.Black
                    )
                    Text(
                        "Paiement : ${order.paymentMethod}",
                        fontSize = PlatformConfig.orderDetailsSize.sp,
                        color = Color(0xFFA0522D)
                    )
                }
                IconButton(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier.size(PlatformConfig.iconSize.dp)
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expand Order",
                        tint = Color(0xFFA0522D)
                    )
                }
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(PlatformConfig.itemSpacing.dp))
                order.items.forEach { item ->
                    OrderItemView(item = item)
                }
            }
        }
    }
}

@Composable
fun OrderItemView(item: OrderItem) {
    val cheesePrice = item.extraCheese * 0.02
    val totalPrice = item.pizza.price + cheesePrice

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PlatformConfig.cardPadding.dp)
            .background(Color(0xFFFFF8E1))
            .padding(PlatformConfig.cardPadding.dp)
    ) {
        Text(
            item.pizza.name,
            fontSize = PlatformConfig.headerTextSize.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E8560)
        )
        Text(
            "Quantité : ${item.quantity}",
            fontSize = PlatformConfig.bodyTextSize.sp,
            color = Color.Black
        )
        Text(
            if (item.extraCheese > 0)
                "Fromage supplémentaire : ${item.extraCheese} g (${round(cheesePrice * 100) / 100}€)"
            else
                "Pas de fromage supplémentaire",
            fontSize = PlatformConfig.bodyTextSize.sp,
            color = Color(0xFFA0522D)
        )
        Text(
            "Prix total : ${round(totalPrice * 100) / 100}€",
            fontSize = PlatformConfig.bodyTextSize.sp,
            color = Color.Black
        )
    }
}
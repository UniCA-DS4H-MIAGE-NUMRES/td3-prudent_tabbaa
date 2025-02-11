package fr.unica.miage.numres.tabbaa.pizzapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.unica.miage.numres.tabbaa.pizzapp.data.OrderRepository
import fr.unica.miage.numres.tabbaa.pizzapp.model.Order
import fr.unica.miage.numres.tabbaa.pizzapp.model.OrderItem
import fr.unica.miage.numres.tabbaa.pizzapp.navigation.NavControllerWrapper
/*import fr.unica.miage.numres.tabbaa.pizzapp.utils.getCurrentDate*/
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.round

@Composable
fun PaymentScreen(
    navController: NavControllerWrapper,
    cartItems: StateFlow<List<OrderItem>>,
    onClearCart: () -> Unit,
    onAddOrder: (String) -> Unit,
    orderRepository: OrderRepository
) {
    val cartItemsState by cartItems.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val totalPrice by derivedStateOf {
        cartItemsState.sumOf { item ->
            (item.pizza.price * item.quantity) + (item.extraCheese * 0.02)
        }
    }

    var selectedPaymentMethod by remember { mutableStateOf<String?>(null) }
    var isPaymentSuccess by remember { mutableStateOf(false) }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color(0xFFFFF8E1),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Paiement",
                        style = MaterialTheme.typography.h6,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("CaddyScreen")
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour",
                            tint = Color.White
                        )
                    }
                },
                backgroundColor = Color(0xFFE3B58A)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Récapitulatif",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF1E8560),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(
                            color = Color.Transparent,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Total à payer : ${round(totalPrice * 100) / 100}€",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                PaymentOption(
                    label = "Carte Bancaire",
                    isSelected = selectedPaymentMethod == "Carte Bancaire",
                    onSelect = { selectedPaymentMethod = "Carte Bancaire" }
                )
                PaymentOption(
                    label = "Carte Ticket Restaurant",
                    isSelected = selectedPaymentMethod == "Carte Ticket Restaurant",
                    onSelect = { selectedPaymentMethod = "Carte Ticket Restaurant" }
                )
                PaymentOption(
                    label = "PayPal",
                    isSelected = selectedPaymentMethod == "PayPal",
                    onSelect = { selectedPaymentMethod = "PayPal" }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (selectedPaymentMethod != null) {
                        coroutineScope.launch {
                            val order = Order(
                                date = /*getCurrentDate(),*/ "01/01/1987",
                                totalPrice = totalPrice,
                                paymentMethod = selectedPaymentMethod!!,
                                items = cartItemsState
                            )
                            orderRepository.addOrder(order)
                            onClearCart()
                            isPaymentSuccess = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA0522D))
            ) {
                Text("Payer", fontSize = 16.sp, color = Color.White)
            }
        }
    }

    if (isPaymentSuccess) {
        AlertDialog(
            onDismissRequest = { isPaymentSuccess = false },
            backgroundColor = Color(0xFFFFF8E1),
            title = {
                Text(
                    text = "Paiement Réussi",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD32F2F)
                )
            },
            text = {
                Text(
                    "Votre paiement a été effectué avec succès. Merci pour votre commande !",
                    style = MaterialTheme.typography.body1,
                    color = Color.Black
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        isPaymentSuccess = false
                        navController.navigate("HomeScreen")
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA0522D))
                ) {
                    Text("OK", style = MaterialTheme.typography.subtitle1, color = Color.White)
                }
            }
        )
    }
}

@Composable
fun PaymentOption(
    label: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable(onClick = onSelect)
            .padding(4.dp),
        backgroundColor = if (isSelected) Color(0xFFA0522D) else Color(0xFFE3B58A)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color(0xFFFFF8E1) else Color.Black
            )
            if (isSelected) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Sélectionné",
                    tint = Color.Black
                )
            }
        }
    }
}
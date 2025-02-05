package fr.unica.miage.tabbaa.pizzapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.unica.miage.tabbaa.pizzapp.viewmodel.OrderViewModel

@Composable
fun OrderScreen(viewModel: OrderViewModel) {
    val orders by viewModel.orders.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        orders.forEach { order ->
            Text("Commande du ${order.date} - Total: ${order.totalPrice}€")
        }
    }
}

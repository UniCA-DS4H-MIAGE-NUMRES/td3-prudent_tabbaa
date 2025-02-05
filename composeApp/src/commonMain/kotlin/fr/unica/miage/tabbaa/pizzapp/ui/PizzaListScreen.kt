package fr.unica.miage.tabbaa.pizzapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.unica.miage.tabbaa.pizzapp.model.Pizza
import fr.unica.miage.tabbaa.pizzapp.viewmodel.PizzaViewModel

@Composable
fun PizzaListScreen(viewModel: PizzaViewModel) {
    val pizzas by viewModel.pizzas.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        pizzas.forEach { pizza ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(pizza.name)
                Button(onClick = { /* Ajouter au panier */ }) {
                    Text("Ajouter")
                }
            }
        }
    }
}

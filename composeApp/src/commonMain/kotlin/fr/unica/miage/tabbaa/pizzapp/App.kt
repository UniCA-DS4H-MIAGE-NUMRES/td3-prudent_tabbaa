package fr.unica.miage.tabbaa.pizzapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.unica.miage.tabbaa.pizzapp.data.DataSource
import fr.unica.miage.tabbaa.pizzapp.data.DataSourceFactory
import fr.unica.miage.tabbaa.pizzapp.model.Pizza
import org.jetbrains.compose.resources.painterResource

@Composable
fun App() {
    MaterialTheme {
        val dataSource = remember { DataSourceFactory.getInstance() }
        val pizzas = dataSource.getPizzas()

        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Menu des Pizzas") })
            },
            content = { padding ->
                LazyColumn(modifier = Modifier.padding(padding)) {
                    items(pizzas) { pizza ->
                        PizzaItem(pizza)
                    }
                }
            }
        )
    }
}


@Composable
fun PizzaItem(pizza: Pizza) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = pizza.name, style = MaterialTheme.typography.h6)
            Text(text = "Prix: ${pizza.price}€", style = MaterialTheme.typography.body1)
        }
    }
}

package fr.unica.miage.tabbaa.pizzapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.unica.miage.tabbaa.pizzapp.data.DataSourceFactory
import fr.unica.miage.tabbaa.pizzapp.model.Pizza
import fr.unica.miage.tabbaa.pizzapp.navigation.NavControllerWrapper
import fr.unica.miage.tabbaa.pizzapp.utils.loadImage
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavControllerWrapper) {
    val dataSource = remember { DataSourceFactory.getInstance() }
    val pizzas = dataSource.getPizzas()

    Scaffold(
        containerColor = Color(0xFFFFF8E1),
        topBar = {
            TopAppBar(
                title = { Text("Menu des Pizzas", style = MaterialTheme.typography.titleLarge, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("HomeScreen") }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFE3B58A))
            )
        },
        content = { innerPadding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(pizzas) { pizza ->
                    PizzaCard(
                        pizza = pizza,
                        onClickPizza = {
                            navController.navigate("PizzaScreen/${pizza.id}")
                        }
                    )
                }
            }
        }
    )
}


@Composable
fun PizzaCard(
    pizza: Pizza,
    onClickPizza: () -> Unit
) {
    Card(
        modifier = Modifier
            .clickable(onClick = { onClickPizza() })
            .shadow(
                elevation = 8.dp,
                shape = MaterialTheme.shapes.medium
            )
            .clip(MaterialTheme.shapes.medium)
            .size(width = 160.dp, height = 200.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF8E1)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Charge dynamiquement l'image en utilisant `loadImage`
            Image(
                painter = loadImage(pizza.image),
                contentDescription = pizza.name,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = pizza.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF1E8560),
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "Prix: ${round(pizza.price * 100) / 100}€",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}

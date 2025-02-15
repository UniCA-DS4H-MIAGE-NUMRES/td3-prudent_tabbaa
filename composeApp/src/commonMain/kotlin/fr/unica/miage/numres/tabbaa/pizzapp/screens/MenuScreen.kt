package fr.unica.miage.numres.tabbaa.pizzapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
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
import androidx.compose.ui.unit.sp
import fr.unica.miage.numres.tabbaa.pizzapp.data.DataSourceFactory
import fr.unica.miage.numres.tabbaa.pizzapp.model.Pizza
import fr.unica.miage.numres.tabbaa.pizzapp.navigation.NavControllerWrapper
import fr.unica.miage.numres.tabbaa.pizzapp.utils.PlatformConfig
import org.jetbrains.compose.resources.painterResource
import pizzapp.composeapp.generated.resources.Res
import pizzapp.composeapp.generated.resources.*
import kotlin.math.round

@Composable
fun MenuScreen(navController: NavControllerWrapper) {
    val dataSource = remember { DataSourceFactory.getInstance() }
    val pizzas = dataSource.getPizzas()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color(0xFFFFF8E1),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Menu des Pizzas",
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
                backgroundColor = Color(0xFFE3B58A)
            )
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(if (PlatformConfig.isWeb) 3 else 2),
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(PlatformConfig.screenPadding.dp),
            verticalArrangement = Arrangement.spacedBy(PlatformConfig.gridSpacing.dp),
            horizontalArrangement = Arrangement.spacedBy(PlatformConfig.gridSpacing.dp)
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
}

@Composable
fun PizzaCard(
    pizza: Pizza,
    onClickPizza: () -> Unit
) {
    val imageRes = when (pizza.image) {
        "pizza1" -> Res.drawable.pizza1
        "pizza2" -> Res.drawable.pizza2
        "pizza3" -> Res.drawable.pizza3
        "pizza4" -> Res.drawable.pizza4
        "pizza5" -> Res.drawable.pizza5
        "pizza6" -> Res.drawable.pizza6
        "pizza7" -> Res.drawable.pizza7
        "pizza8" -> Res.drawable.pizza8
        "pizza9" -> Res.drawable.pizza9
        else -> Res.drawable.logo
    }

    Card(
        modifier = Modifier
            .clickable(onClick = onClickPizza)
            .shadow(
                elevation = PlatformConfig.cardElevation.dp,
                shape = MaterialTheme.shapes.medium
            )
            .clip(MaterialTheme.shapes.medium)
            .size(
                width = PlatformConfig.cardWidth.dp,
                height = PlatformConfig.cardHeight.dp
            ),
        backgroundColor = Color(0xFFFFF8E1)
    ) {
        Column(
            modifier = Modifier
                .padding(PlatformConfig.cardPadding.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = pizza.name,
                modifier = Modifier.size(PlatformConfig.imageSize.dp)
            )
            Text(
                text = pizza.name,
                fontSize = PlatformConfig.pizzaNameSize.sp,
                color = Color(0xFF1E8560),
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "Prix: ${round(pizza.price * 100) / 100}€",
                fontSize = PlatformConfig.pizzaPriceSize.sp,
                color = Color.Black
            )
        }
    }
}
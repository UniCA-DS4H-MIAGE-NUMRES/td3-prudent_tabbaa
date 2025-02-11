package fr.unica.miage.numres.tabbaa.pizzapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.unica.miage.numres.tabbaa.pizzapp.model.Pizza
import fr.unica.miage.numres.tabbaa.pizzapp.navigation.NavControllerWrapper
import fr.unica.miage.numres.tabbaa.pizzapp.utils.PlatformConfig
import org.jetbrains.compose.resources.painterResource
import pizzapp.composeapp.generated.resources.Res
import pizzapp.composeapp.generated.resources.*
import kotlin.math.round

@Composable
fun PizzaScreen(
    pizza: Pizza,
    navController: NavControllerWrapper,
    onAddToCart: (Pizza, Int) -> Unit
) {
    var extraCheese by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()

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

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color(0xFFFFF8E1),
        topBar = {
            TopAppBar(
                title = { Text("Détails de la Pizza", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("MenuScreen") }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour", tint = Color.White)
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
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = pizza.name,
                modifier = Modifier.size(180.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(pizza.name, fontSize = 28.sp, color = Color(0xFF1E8560))
            Text("Prix: ${round(pizza.price * 100) / 100}€", fontSize = 20.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))

            Text("Ingrédients :", fontSize = 20.sp, color = Color(0xFFA0522D))
            Spacer(modifier = Modifier.height(8.dp))
            pizza.ingredients.forEach { ingredient ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .background(Color(0xFFE3B58A), shape = MaterialTheme.shapes.medium)
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.Check, contentDescription = null, tint = Color(0xFFE63946))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(ingredient, style = MaterialTheme.typography.body1)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Fromage supplémentaire : $extraCheese g", fontSize = 16.sp)
            Slider(
                value = extraCheese.toFloat(),
                onValueChange = { extraCheese = (round(it / 10) * 10).toInt() },
                valueRange = 0f..100f,
                steps = 9,
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFFE63946),
                    activeTrackColor = Color(0xFFFFC107)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onAddToCart(pizza, extraCheese) },
                modifier = Modifier
                    .fillMaxWidth(PlatformConfig.buttonWidth)
                    .height(PlatformConfig.buttonHeight.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFA0522D),
                    contentColor = Color.White
                )
            ) {
                Text("Ajouter au Panier")
            }
        }
    }
}
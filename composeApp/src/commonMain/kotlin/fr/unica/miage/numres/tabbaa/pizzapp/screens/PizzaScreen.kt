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
    var showSnackbar by remember { mutableStateOf(false) } // État pour afficher le Snackbar

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
                title = {
                    Text(
                        "Détails de la Pizza",
                        fontSize = PlatformConfig.titleSize.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("MenuScreen") },
                        modifier = Modifier.size(PlatformConfig.iconSize.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
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
        },
        snackbarHost = {
            SnackbarHost(hostState = scaffoldState.snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(PlatformConfig.screenPadding.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = pizza.name,
                modifier = Modifier.size(PlatformConfig.pizzaImageSize.dp)
            )

            Spacer(modifier = Modifier.height(PlatformConfig.elementSpacing.dp))

            Text(
                pizza.name,
                fontSize = PlatformConfig.pizzaNameSize.sp,
                color = Color(0xFF1E8560)
            )
            Text(
                "Prix: ${round(pizza.price * 100) / 100}€",
                fontSize = PlatformConfig.pizzaPriceSize.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(PlatformConfig.elementSpacing.dp))

            Text(
                "Ingrédients :",
                fontSize = PlatformConfig.ingredientsTitleSize.sp,
                color = Color(0xFFA0522D)
            )

            Spacer(modifier = Modifier.height(PlatformConfig.ingredientSpacing.dp))

            pizza.ingredients.forEach { ingredient ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(vertical = PlatformConfig.ingredientSpacing.dp)
                        .background(Color(0xFFE3B58A), shape = MaterialTheme.shapes.medium)
                        .padding(PlatformConfig.ingredientPadding.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = null,
                        tint = Color(0xFFE63946),
                        modifier = Modifier.size(PlatformConfig.iconSize.dp)
                    )
                    Spacer(modifier = Modifier.width(PlatformConfig.ingredientSpacing.dp))
                    Text(
                        ingredient,
                        fontSize = PlatformConfig.ingredientTextSize.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(PlatformConfig.elementSpacing.dp))

            Text(
                "Fromage supplémentaire : $extraCheese g",
                fontSize = PlatformConfig.cheeseTextSize.sp
            )

            Slider(
                value = extraCheese.toFloat(),
                onValueChange = { extraCheese = (round(it / 10) * 10).toInt() },
                valueRange = 0f..100f,
                steps = 9,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(PlatformConfig.sliderHeight.dp),
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFFE63946),
                    activeTrackColor = Color(0xFFFFC107)
                )
            )

            Spacer(modifier = Modifier.height(PlatformConfig.elementSpacing.dp))

            Button(
                onClick = {
                    onAddToCart(pizza, extraCheese)
                    showSnackbar = true // Déclenche l'affichage du Snackbar
                },
                modifier = Modifier
                    .fillMaxWidth(PlatformConfig.buttonWidth)
                    .height(PlatformConfig.buttonHeight.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFA0522D),
                    contentColor = Color.White
                )
            ) {
                Text(
                    "Ajouter au Panier",
                    fontSize = PlatformConfig.bottomTextSiza.sp
                )
            }
        }
    }

    LaunchedEffect(showSnackbar) {
        if (showSnackbar) {
            scaffoldState.snackbarHostState.showSnackbar("Pizza ajoutée au panier !")
            showSnackbar = false
        }
    }
}
package fr.unica.miage.numres.tabbaa.pizzapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.unica.miage.numres.tabbaa.pizzapp.navigation.NavControllerWrapper
import fr.unica.miage.numres.tabbaa.pizzapp.utils.PlatformConfig
import org.jetbrains.compose.resources.painterResource
import pizzapp.composeapp.generated.resources.Res
import pizzapp.composeapp.generated.resources.logo

@Composable
fun HomeScreen(navController: NavControllerWrapper) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color(0xFFFFF8E1)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(PlatformConfig.contentPadding.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "PizzaApp Logo",
                modifier = Modifier.size(PlatformConfig.logoSize.dp)
            )

            Spacer(modifier = Modifier.height(PlatformConfig.spacerHeight.dp))

            Text(
                text = "PizzaApp",
                fontSize = PlatformConfig.titleSize.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Les meilleures pizzas italiennes livrées chez vous !",
                fontSize = PlatformConfig.subtitleSize.sp,
                color = Color(0xFF8D6E63),
                modifier = Modifier.padding(horizontal = PlatformConfig.contentPadding.dp)
            )

            Spacer(modifier = Modifier.height(PlatformConfig.spacerHeight.dp))

            HomeButton(
                text = "Voir le menu",
                colors = listOf(Color(0xFFE63946), Color(0xFFF4A261)),
                onClick = { navController.navigate("MenuScreen") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            HomeButton(
                text = "Voir le panier",
                colors = listOf(Color(0xFF2A9D8F), Color(0xFF1E8560)),
                onClick = { navController.navigate("CaddyScreen") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            HomeButton(
                text = "Voir l'historique des commandes",
                colors = listOf(Color(0xFFE3B58A), Color(0xFF8D6E63)),
                onClick = { navController.navigate("CommandeHistoryScreen") }
            )
        }
    }
}

@Composable
private fun HomeButton(
    text: String,
    colors: List<Color>,
    onClick: () -> Unit
) {
    GradientButton(
        text = text,
        gradient = Brush.horizontalGradient(colors = colors),
        onClick = onClick
    )
}

@Composable
fun GradientButton(
    text: String,
    gradient: Brush,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(PlatformConfig.buttonWidth)
            .height(PlatformConfig.buttonHeight.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient, shape = MaterialTheme.shapes.medium),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = PlatformConfig.bottomTextSiza.sp,
                color = Color.White
            )
        }
    }
}
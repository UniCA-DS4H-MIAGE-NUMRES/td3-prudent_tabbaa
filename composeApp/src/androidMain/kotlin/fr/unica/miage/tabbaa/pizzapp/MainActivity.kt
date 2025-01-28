package fr.unica.miage.tabbaa.pizzapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import fr.unica.miage.tabbaa.pizzapp.ui.theme.PizzAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PizzAppTheme {
                MyApp() // Appel de la classe MyApp qui contient la logique de l'application
            }
        }
    }
}

package fr.unica.miage.tabbaa.pizzapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import fr.unica.miage.tabbaa.pizzapp.viewmodel.PizzaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContext = applicationContext

        setContent {
            val viewModel = PizzaViewModel(PizzaRepository())
            PizzaListScreen(viewModel)
        }
    }
}
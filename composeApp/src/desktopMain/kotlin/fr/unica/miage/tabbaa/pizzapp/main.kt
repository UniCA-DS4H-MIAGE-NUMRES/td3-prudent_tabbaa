package fr.unica.miage.tabbaa.pizzapp

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.singleWindowApplication
import fr.unica.miage.tabbaa.pizzapp.viewmodel.PizzaViewModel

fun main() = singleWindowApplication {
    val viewModel = PizzaViewModel(PizzaRepository())
    PizzaListScreen(viewModel)
}
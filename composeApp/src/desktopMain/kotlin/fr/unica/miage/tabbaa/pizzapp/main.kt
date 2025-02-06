package fr.unica.miage.tabbaa.pizzapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import fr.unica.miage.tabbaa.pizzapp.data.DataSourceFactory
import fr.unica.miage.tabbaa.pizzapp.data.OrderDaoDesktop

fun main() = application {
    // ✅ Initialisation de DataSourceFactory avec OrderDao pour Desktop
    DataSourceFactory.init(OrderDaoDesktop())

    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
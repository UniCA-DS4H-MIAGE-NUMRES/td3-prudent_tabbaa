package fr.unica.miage.numres.tabbaa.pizzapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import fr.unica.miage.numres.tabbaa.pizzapp.data.OrderDaoDesktop
import fr.unica.miage.tabbaa.pizzapp.App
import fr.unica.miage.numres.tabbaa.pizzapp.data.DataSourceFactory

fun main() = application {
    DataSourceFactory.init(OrderDaoDesktop())

    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
package fr.unica.miage.numres.tabbaa.pizzapp

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import fr.unica.miage.numres.tabbaa.pizzapp.data.OrderDaoWasmJs
import fr.unica.miage.tabbaa.pizzapp.App
import fr.unica.miage.numres.tabbaa.pizzapp.data.DataSourceFactory
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    // Initialisation du DAO et du DataSourceFactory
    val orderDao = OrderDaoWasmJs()
    DataSourceFactory.init(orderDao)

    // Lancement de l'application
    ComposeViewport(document.body!!) {
        App()
    }
}
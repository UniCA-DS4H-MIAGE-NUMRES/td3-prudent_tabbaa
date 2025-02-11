package fr.unica.miage.numres.tabbaa.pizzapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import fr.unica.miage.numres.tabbaa.pizzapp.data.AndroidOrderDaoImpl
import fr.unica.miage.numres.tabbaa.pizzapp.data.DataSourceFactory
import fr.unica.miage.numres.tabbaa.pizzapp.data.OrderDao
import fr.unica.miage.tabbaa.pizzapp.App

import fr.unica.miage.numres.tabbaa.pizzapp.data.PizzaDatabase
import org.jetbrains.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = PizzaDatabase.getDatabase(this)
        val androidOrderDao = database.orderDao()

        val orderDao: OrderDao = AndroidOrderDaoImpl(androidOrderDao)

        DataSourceFactory.init(orderDao)

        setContent {
            App()
        }
    }
}


@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
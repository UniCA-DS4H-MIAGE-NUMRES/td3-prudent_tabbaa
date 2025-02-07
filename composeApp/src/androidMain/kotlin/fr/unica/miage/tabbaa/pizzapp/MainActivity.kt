package fr.unica.miage.tabbaa.pizzapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import fr.unica.miage.tabbaa.pizzapp.data.AndroidOrderDaoImpl
import fr.unica.miage.tabbaa.pizzapp.data.DataSourceFactory
import fr.unica.miage.tabbaa.pizzapp.data.OrderDao
import fr.unica.miage.tabbaa.pizzapp.data.PizzaDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisation de la base de données
        val database = PizzaDatabase.getDatabase(this)
        val androidOrderDao = database.orderDao()

        val orderDao: OrderDao = AndroidOrderDaoImpl(androidOrderDao)

        // Initialisation de DataSourceFactory avec OrderDao
        DataSourceFactory.init(orderDao)

        setContent {
            App()
        }
    }
}


package fr.unica.miage.tabbaa.pizzapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import fr.unica.miage.tabbaa.pizzapp.data.DataSource
import fr.unica.miage.tabbaa.pizzapp.data.PizzaDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = PizzaDatabase.getDatabase(this)
        val orderDao = database.orderDao()
        val dataSource = DataSource(orderDao) // ✅ Utilisation du DataSource du common

        setContent {
            App(dataSource)
        }
    }
}

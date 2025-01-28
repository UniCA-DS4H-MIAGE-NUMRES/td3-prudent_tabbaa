package fr.unica.miage.tabbaa.pizzapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import fr.unica.miage.tabbaa.pizzapp.util.OrderConverters

/**
 * Classe qui définit une commande et la table correspondante dans la base de données
 */
@Entity(tableName = "orders") // Table dans la base de données
data class Order(
    @PrimaryKey(autoGenerate = true) val orderId: Int = 0, // Id auto
    val date: String, // Date de la commande
    val totalPrice: Double, // Prix total
    val paymentMethod: String, // Mode de paiement
    @TypeConverters(OrderConverters::class) val items: List<OrderItem> // Conversion personnalisée
)

@Entity(tableName = "order_items") // Table dans la base de données
data class OrderItem(
    val pizza: Pizza, // Pizza commandée
    val quantity: Int, // Quantité commandée
    val extraCheese: Int, // Fromage supplémentaire
    @PrimaryKey(autoGenerate = true) val id: Int = 0 // Id auto
)


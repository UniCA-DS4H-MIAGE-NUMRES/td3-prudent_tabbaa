package fr.unica.miage.tabbaa.pizzapp.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.unica.miage.tabbaa.pizzapp.model.OrderItem

/**
 * Classe pour convertir les objets en JSON
 * Nécessaire car on utilise une liste personnalisée dans la classe Order et Room ne sait pas comment la gérer
 */
class OrderConverters {
    private val gson = Gson() // Objet Gson pour la conversion

    /**
     * Convertit une liste d'items en JSON
     */
    @TypeConverter
    fun fromOrderItemList(items: List<OrderItem>): String {
        return gson.toJson(items) // Conversion en JSON
    }

    /**
     * Convertit une chaîne JSON en liste d'items
     */
    @TypeConverter
    fun toOrderItemList(data: String): List<OrderItem> {
        val type = object : TypeToken<List<OrderItem>>() {}.type // Type de la liste
        return gson.fromJson(data, type) // Conversion en liste
    }
}

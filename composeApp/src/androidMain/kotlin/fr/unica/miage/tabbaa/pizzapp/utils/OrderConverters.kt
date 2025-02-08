package fr.unica.miage.tabbaa.pizzapp.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.unica.miage.tabbaa.pizzapp.model.OrderItem

/**
 * Classe pour convertir la liste d'OrderItem en JSON pour Room
 */
class OrderConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromOrderItemList(items: List<OrderItem>): String {
        return gson.toJson(items)
    }

    @TypeConverter
    fun toOrderItemList(data: String): List<OrderItem> {
        val type = object : TypeToken<List<OrderItem>>() {}.type
        return gson.fromJson(data, type)
    }
}

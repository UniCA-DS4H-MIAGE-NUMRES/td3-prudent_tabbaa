package fr.unica.miage.numres.tabbaa.pizzapp.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.unica.miage.numres.tabbaa.pizzapp.model.OrderItem

class OrderConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromOrderItemList(orderItems: List<OrderItem>?): String {
        return gson.toJson(orderItems)
    }

    @TypeConverter
    fun toOrderItemList(orderItemsString: String?): List<OrderItem> {
        return if (orderItemsString.isNullOrEmpty()) {
            emptyList()
        } else {
            val type = object : TypeToken<List<OrderItem>>() {}.type
            val orderItems = gson.fromJson<List<OrderItem>>(orderItemsString, type)

            orderItems.forEach { it.pizza = it.pizza.copy(image = it.pizza.image.toString()) }

            orderItems
        }
    }
}

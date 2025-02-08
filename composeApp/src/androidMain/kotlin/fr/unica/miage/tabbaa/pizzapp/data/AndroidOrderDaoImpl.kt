package fr.unica.miage.tabbaa.pizzapp.data

import fr.unica.miage.tabbaa.pizzapp.model.toEntity
import fr.unica.miage.tabbaa.pizzapp.model.toOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AndroidOrderDaoImpl(private val androidDao: AndroidOrderDao) : OrderDao {
    override suspend fun insertOrder(order: fr.unica.miage.tabbaa.pizzapp.model.Order) {
        androidDao.insertOrder(order.toEntity())
    }

    override fun getAllOrders(): Flow<List<fr.unica.miage.tabbaa.pizzapp.model.Order>> {
        return androidDao.getAllOrders().map { list -> list.map { it.toOrder() } }
    }

    override suspend fun deleteAllOrders() {
        androidDao.deleteAllOrders()
    }

    override suspend fun refreshOrders() {
        androidDao.getAllOrders().first()
    }
}

package fr.unica.miage.tabbaa.pizzapp.data

object DataSourceFactoryDesktop {
    fun create(): DataSource {
        val orderDao = OrderDaoDesktop()
        return DataSource(orderDao)
    }
}

package fr.unica.miage.numres.tabbaa.pizzapp.data

object DataSourceFactoryDesktop {
    fun create(): DataSource {
        val orderDao = OrderDaoDesktop()
        return DataSource(orderDao)
    }
}

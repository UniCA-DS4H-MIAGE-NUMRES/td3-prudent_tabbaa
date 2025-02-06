package fr.unica.miage.tabbaa.pizzapp.data

object DataSourceFactory {
    private lateinit var instance: DataSource

    fun init(orderDao: OrderDao) {
        instance = DataSource(orderDao)
    }

    fun getInstance(): DataSource {
        if (!::instance.isInitialized) throw IllegalStateException("DataSourceFactory not initialized")
        return instance
    }
}

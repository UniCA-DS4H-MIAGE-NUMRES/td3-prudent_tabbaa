package fr.unica.miage.numres.tabbaa.pizzapp.data

object DataSourceFactory {
    private lateinit var instance: DataSource
    private lateinit var orderRepository: OrderRepository

    fun init(orderDao: OrderDao) {
        instance = DataSource(orderDao)
        orderRepository = OrderRepository(orderDao)
    }

    fun getInstance(): DataSource {
        if (!DataSourceFactory::instance.isInitialized) throw IllegalStateException("DataSourceFactory not initialized")
        return instance
    }

    fun getOrderRepository(): OrderRepository {
        if (!DataSourceFactory::orderRepository.isInitialized) throw IllegalStateException("OrderRepository has not been initialized.")
        return orderRepository
    }
}


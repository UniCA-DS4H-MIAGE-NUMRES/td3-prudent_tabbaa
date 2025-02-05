package fr.unica.miage.tabbaa.pizzapp.data

import app.cash.sqldelight.db.SqlDriver

expect fun getDatabaseDriver(): SqlDriver

object DatabaseFactory {
    fun createDatabase(): PizzaDatabase {
        return PizzaDatabase(getDatabaseDriver())
    }
}

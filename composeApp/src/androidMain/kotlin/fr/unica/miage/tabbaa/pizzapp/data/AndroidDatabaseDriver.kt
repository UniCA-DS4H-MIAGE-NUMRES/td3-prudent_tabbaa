package fr.unica.miage.tabbaa.pizzapp.data

import android.content.Context
import app.cash.sqldelight.android.AndroidSqliteDriver
import app.cash.sqldelight.db.SqlDriver

lateinit var appContext: Context

actual fun getDatabaseDriver(): SqlDriver {
    return AndroidSqliteDriver(PizzaDatabase.Schema, appContext, "pizza.db")
}

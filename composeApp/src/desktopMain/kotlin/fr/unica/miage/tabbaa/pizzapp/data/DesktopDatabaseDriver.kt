package fr.unica.miage.tabbaa.pizzapp.data

import app.cash.sqldelight.db.SqlDriver

actual fun getDatabaseDriver(): SqlDriver? = null // Pas de base de données sur le Web

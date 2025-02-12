package fr.unica.miage.numres.tabbaa.pizzapp.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

actual fun getCurrentDate(): String {
    val currentMoment = Clock.System.now()
    val localDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
    return "%02d/%02d/%04d".format(localDateTime.dayOfMonth, localDateTime.monthNumber, localDateTime.year)
}
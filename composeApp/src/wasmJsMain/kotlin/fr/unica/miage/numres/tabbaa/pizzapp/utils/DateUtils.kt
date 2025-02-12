package fr.unica.miage.numres.tabbaa.pizzapp.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

actual fun getCurrentDate(): String {
    val currentMoment = Clock.System.now()
    val localDateTime = currentMoment.toLocalDateTime(TimeZone.UTC)
    val day = localDateTime.dayOfMonth.toString().padStart(2, '0')
    val month = localDateTime.monthNumber.toString().padStart(2, '0')
    val year = localDateTime.year.toString()
    return "$day/$month/$year"
}
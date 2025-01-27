package fr.unica.miage.tabbaa.pizzapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
package fr.unica.miage.numres.tabbaa.pizzapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
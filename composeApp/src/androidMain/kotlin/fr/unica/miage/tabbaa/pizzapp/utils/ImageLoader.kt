package fr.unica.miage.tabbaa.pizzapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import fr.unica.miage.tabbaa.pizzapp.R

@Composable
actual fun loadImage(resourcePath: String): Painter {
    return when (resourcePath) {
        "logo.png" -> painterResource(R.drawable.logo)
        else -> error("Image introuvable")
    }
}

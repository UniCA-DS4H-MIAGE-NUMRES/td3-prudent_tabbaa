package fr.unica.miage.tabbaa.pizzapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import fr.unica.miage.tabbaa.pizzapp.R

@Composable
actual fun loadImage(resourcePath: String): Painter {
    val resourceId = when (resourcePath) {
        "logo.png" -> R.drawable.logo
        "pizza1.png" -> R.drawable.pizza1
        "pizza2.png" -> R.drawable.pizza2
        "pizza3.png" -> R.drawable.pizza3
        "pizza4.png" -> R.drawable.pizza4
        "pizza5.png" -> R.drawable.pizza5
        "pizza6.png" -> R.drawable.pizza6
        "pizza7.png" -> R.drawable.pizza7
        "pizza8.png" -> R.drawable.pizza8
        "pizza9.png" -> R.drawable.pizza9
        else -> throw IllegalStateException("Image introuvable")
    }
    return painterResource(resourceId)
}

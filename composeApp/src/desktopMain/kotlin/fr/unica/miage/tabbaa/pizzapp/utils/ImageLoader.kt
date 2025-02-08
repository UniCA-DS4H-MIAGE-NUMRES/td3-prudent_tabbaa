package fr.unica.miage.tabbaa.pizzapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

@Composable
actual fun loadImage(resourcePath: String): Painter {
    return painterResource(resourcePath)
}

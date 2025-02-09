package fr.unica.miage.tabbaa.pizzapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class, InternalResourceApi::class)
@Composable
actual fun loadImage(resourcePath: String): Painter {
    val drawable = DrawableResource("drawable/$resourcePath", emptySet())
    return painterResource(drawable)
}
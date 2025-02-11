package fr.unica.miage.numres.tabbaa.pizzapp.utils

actual object PlatformConfig {
    actual val buttonWidth: Float
        get() = 200f
    actual val buttonHeight: Float
        get() = 50f
    actual val textSize: Float
        get() = 16f
    actual val isAndroid: Boolean
        get() = false
    actual val isWeb: Boolean
        get() = true
}
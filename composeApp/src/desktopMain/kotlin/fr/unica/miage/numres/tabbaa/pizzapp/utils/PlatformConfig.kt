package fr.unica.miage.numres.tabbaa.pizzapp.utils

actual object PlatformConfig {
    actual val buttonWidth: Float = 0.3f
    actual val buttonHeight: Float = 60f
    actual val bottomTextSiza: Float = 18f
    actual val isAndroid: Boolean = false
    actual val isWeb: Boolean = false
    actual val titleSize: Float = 24f

    // HomeScreen
    actual val subtitleSize: Float = 20f
    actual val logoSize: Float = 250f
    actual val contentPadding: Float = 20f
    actual val spacerHeight: Float = 24f

    // CaddyScreen
    actual val cardElevation: Float = 4f
    actual val cardPadding: Float = 10f
    actual val itemSpacing: Float = 16f
    actual val priceSize: Float = 16f
    actual val descriptionSize: Float = 14f
    actual val iconSize: Float = 24f
    actual val quantitySize: Float = 16f
    actual val totalPriceSize: Float = 20f
    actual val screenPadding: Float = 20f
    actual val cartItemPadding: Float = 10f

    // CommandeHistoryScreen
    actual val headerTextSize: Float = 20f
    actual val bodyTextSize: Float = 14f
    actual val emptyMessageSize: Float = 18f
    actual val orderDateSize: Float = 16f
    actual val orderTotalSize: Float = 16f
    actual val orderDetailsSize: Float = 14f

    // MenuScreen
    actual val gridSpacing: Float = 12f
    actual val cardWidth: Float = 200f
    actual val cardHeight: Float = 240f
    actual val imageSize: Float = 100f
    actual val pizzaNameSize: Float = 18f
    actual val pizzaPriceSize: Float = 16f

    // PaymentScreen
    actual val summaryTitleSize: Float = 24f
    actual val paymentOptionHeight: Float = 60f
    actual val paymentOptionTextSize: Float = 16f
    actual val paymentButtonTextSize: Float = 16f
    actual val dialogTitleSize: Float = 22f
    actual val dialogMessageSize: Float = 16f
    actual val dialogButtonTextSize: Float = 14f
    actual val elementSpacing: Float = 20f

    // PizzaScreen
    actual val pizzaImageSize: Float = 200f
    actual val ingredientsTitleSize: Float = 20f
    actual val ingredientTextSize: Float = 14f
    actual val cheeseTextSize: Float = 16f
    actual val ingredientSpacing: Float = 10f
    actual val ingredientPadding: Float = 10f
    actual val sliderHeight: Float = 48f
}
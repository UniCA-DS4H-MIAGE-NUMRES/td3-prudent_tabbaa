package fr.unica.miage.numres.tabbaa.pizzapp.utils

expect object PlatformConfig {
    val buttonWidth: Float
    val buttonHeight: Float
    val bottomTextSiza: Float
    val isAndroid: Boolean
    val isWeb: Boolean
    val titleSize: Float

    // HomeScreen
    val subtitleSize: Float
    val logoSize: Float
    val contentPadding: Float
    val spacerHeight: Float

    // CaddyScreen
    val cardElevation: Float
    val cardPadding: Float
    val itemSpacing: Float
    val priceSize: Float
    val descriptionSize: Float
    val iconSize: Float
    val quantitySize: Float
    val totalPriceSize: Float
    val screenPadding: Float
    val cartItemPadding: Float

    // CommandeHistoryScreen
    val headerTextSize: Float
    val bodyTextSize: Float
    val emptyMessageSize: Float
    val orderDateSize: Float
    val orderTotalSize: Float
    val orderDetailsSize: Float

    // MenuScreen
    val gridSpacing: Float
    val cardWidth: Float
    val cardHeight: Float
    val imageSize: Float
    val pizzaNameSize: Float
    val pizzaPriceSize: Float

    // PizzaScreen
    val summaryTitleSize: Float
    val paymentOptionHeight: Float
    val paymentOptionTextSize: Float
    val paymentButtonTextSize: Float
    val dialogTitleSize: Float
    val dialogMessageSize: Float
    val dialogButtonTextSize: Float
    val elementSpacing: Float

    // PizzaCard
    val pizzaImageSize: Float
    val ingredientsTitleSize: Float
    val ingredientTextSize: Float
    val cheeseTextSize: Float
    val ingredientSpacing: Float
    val ingredientPadding: Float
    val sliderHeight: Float
}

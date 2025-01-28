package fr.unica.miage.tabbaa.pizzapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.unica.miage.tabbaa.pizzapp.BottomBar

/**
 * Écran de paiement
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    navController: NavController, // Contrôleur de navigation
    totalPrice: Double, // Prix total
    onClearCart: () -> Unit, // Fonction pour vider le panier
    onAddOrder: (String) -> Unit // Fonction pour ajouter une commande
) {
    val TAG = "PizzaApp-Log"
    var selectedPaymentMethod by remember { mutableStateOf<String?>(null) } // Méthode de paiement sélectionnée
    var isPaymentSuccess by remember { mutableStateOf(false) } // Paiement réussi

    Scaffold(
        containerColor = Color(0xFFFFF8E1),
        topBar = {
            TopAppBar( // Header
                title = {
                    Text(
                        text = "Paiement",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                navigationIcon = { // Bouton de retour
                    IconButton(onClick = {
                        Log.i(TAG, "Navigation vers l'écran précédent")
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE3B58A),
                    titleContentColor = Color(0xFFFFF8E1),
                    actionIconContentColor = Color(0xFFFFF8E1),
                )
            )
        },
        bottomBar = {
            BottomBar(navController = navController) // Ajout de la BottomBar
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text( // Titre
                        text = "Récapitulatif",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF1E8560),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Box( // Box qui contient les dinfos Total à payer
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(
                                color = Color.Transparent,
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text( // Prix total
                            text = "Total à payer : ${"%.2f".format(totalPrice)}€",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column( // Méthodes de paiement
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PaymentOption( // Paiement par carte bancaire
                        label = "Carte Bancaire",
                        isSelected = selectedPaymentMethod == "Carte Bancaire",
                        onSelect = {
                            Log.d(TAG, "Méthode de paiement sélectionnée : Carte Bancaire")
                            selectedPaymentMethod = "Carte Bancaire"
                        }
                    )
                    PaymentOption( // Paiement par Ticket Restaurant
                        label = "Carte Ticket Restaurant",
                        isSelected = selectedPaymentMethod == "Carte Ticket Restaurant",
                        onSelect = {
                            Log.d(TAG, "Méthode de paiement sélectionnée : Carte Ticket Restau")
                            selectedPaymentMethod = "Carte Ticket Restaurant"
                        }
                    )
                    PaymentOption( // Paiement par PayPal
                        label = "PayPal",
                        isSelected = selectedPaymentMethod == "PayPal",
                        onSelect = {
                            Log.d(TAG, "Méthode de paiement sélectionnée : Paypal")
                            selectedPaymentMethod = "PayPal"
                        }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button( // Bouton pour payer
                    onClick = {
                        if (selectedPaymentMethod != null) {
                            onAddOrder(selectedPaymentMethod!!)
                            onClearCart()
                            isPaymentSuccess = true // Paiement réussi (si true on call un dialog)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA0522D))
                ) {
                    Text("Payer", fontSize = 16.sp, color = Color.White)
                }
            }
        }
    )

    if (isPaymentSuccess) { // Si paiement réussi
        AlertDialog( // Affichage dune alerte dialog
            onDismissRequest = { isPaymentSuccess = false }, // Quand on ferme l'alerte
            containerColor = Color(0xFFFFF8E1), // Couleur de fond l'alerte
            title = {
                Text( // Titre de l'alerte
                    text = "Paiement Réussi",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD32F2F)
                )
            },
            text = {
                Text( // Message de l'alerte
                    "Votre paiement a été effectué avec succès. Merci pour votre commande !",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            },
            confirmButton = {
                Button( // Bouton OK
                    onClick = {
                        isPaymentSuccess = false
                        navController.navigate("HomeScreen") // Retour à l'écran d'accueil
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA0522D))
                ) { // Texte du bouton
                    Text("OK", style = MaterialTheme.typography.titleMedium, color = Color.White)
                }
            },
            icon = {
                Icon( // Icone de succès du paiment
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Succès",
                    tint = Color(0xFF1E8560)
                )
            }
        )
    }
}

/**
 * Composant pour afficher une méthode de paiement
 */
@Composable
fun PaymentOption(
    label: String, // Nom de la méthode de paiement
    isSelected: Boolean, // Si la méthode est sélectionnée
    onSelect: () -> Unit // Fonction pour sélectionner la méthode
) {
    Card( // Carte pour identifier la méthode de paiement sélectionnée
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable(onClick = onSelect)
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFA0522D) else Color(0xFFE3B58A),
            contentColor = if (isSelected) Color(0xFFFFF8E1) else Color.Black
        )
    ) {
        Row( // Ligne pour afficher le nom de la méthode de paiement
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text( // Nom de la méthode de paiement
                text = label,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            if (isSelected) { // Si la méthode est sélectionnée alors on affiche une icone
                Icon(imageVector = Icons.Filled.Check, contentDescription = "Sélectionné", tint = Color.Black)
            }
        }
    }
}
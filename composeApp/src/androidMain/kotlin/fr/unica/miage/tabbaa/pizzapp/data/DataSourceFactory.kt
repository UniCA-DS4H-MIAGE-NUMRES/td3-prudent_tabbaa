package fr.unica.miage.tabbaa.pizzapp.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Factory pour créer une instance de DataSource
 * Nécessaire car le constructeur de DataSource prend un paramètre de type OrderDao
 */
class DataSourceFactory(private val orderDao: OrderDao) : ViewModelProvider.Factory {
    /**
     * Méthode pour créer une instance de DataSource
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataSource::class.java)) { // Si le modèle est de type DataSource
            @Suppress("UNCHECKED_CAST") // On supprime l'avertissement
            return DataSource(orderDao) as T // On retourne une instance de DataSource
        }
        throw IllegalArgumentException("Erreur ViewModel") // On lève une exception si le modèle est inconnu
    }
}

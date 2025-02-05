package fr.unica.miage.tabbaa.pizzapp.viewmodel

import fr.unica.miage.tabbaa.pizzapp.data.PizzaRepository
import fr.unica.miage.tabbaa.pizzapp.model.Pizza
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PizzaViewModel(private val repository: PizzaRepository) {
    private val _pizzas = MutableStateFlow<List<Pizza>>(emptyList())
    val pizzas: StateFlow<List<Pizza>> = _pizzas

    fun loadPizzas() {
        _pizzas.value = repository.getAllPizzas()
    }
}

package com.xcvi.micros.ui.comp

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class FoodListComponent(
    componentContext: ComponentContext,
    private val onFoodSelected: (String) -> Unit
) : ComponentContext by componentContext {

    private val viewModel = FoodListViewModel()
    val foods = viewModel.foods

    fun onClick(foodId: String) = onFoodSelected(foodId)
}
class FoodDetailsComponent(
    componentContext: ComponentContext,
    private val foodId: String
) : ComponentContext by componentContext {

    private val viewModel = FoodDetailsViewModel(foodId)

    val food: StateFlow<String> = viewModel.food
}


class FoodListViewModel {
    val foods = listOf("Apple", "Banana", "Bread", "Cheese", "Pizza")
}


class FoodDetailsViewModel(foodId: String) {

    private val _food = MutableStateFlow("Details for $foodId")
    val food: StateFlow<String> = _food.asStateFlow()
}
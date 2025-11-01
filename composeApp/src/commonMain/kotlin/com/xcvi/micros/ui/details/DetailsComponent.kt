package com.xcvi.micros.ui.details

import com.arkivanov.decompose.ComponentContext
import com.xcvi.micros.Food
import com.xcvi.micros.data.FoodRepository

class DetailsComponent(
    context: ComponentContext,
    private val repo: FoodRepository,
    private val foodId: String,
    private val onBack: () -> Unit
) : ComponentContext by context {

    val food: Food? = repo.getFoodById(foodId)

    fun onBackClick() = onBack()
}
package com.xcvi.micros.ui.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.xcvi.micros.Food
import com.xcvi.micros.data.FoodRepository


class HomeComponent(
    context: ComponentContext,
    private val repo: FoodRepository,
    private val onFoodClick: (String) -> Unit
) : ComponentContext by context {

    val foods: List<Food> = repo.getAllFoods()

    fun onFoodSelected(id: String) = onFoodClick(id)
}
package com.xcvi.micros.ui.home

import androidx.lifecycle.lifecycleScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.xcvi.micros.Food
import com.xcvi.micros.data.FoodRepository
import com.xcvi.micros.ui.root.BaseComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class HomeComponent(
    context: ComponentContext,
    private val repo: FoodRepository,
    private val onFoodClick: (String) -> Unit
) : BaseComponent(context) {

    val foods: Flow<List<Food>> = repo.getAllFoods()

    fun insert() {
        scope.launch {
            repo.insertRandomFood()
        }
    }

    fun onFoodSelected(id: String) = onFoodClick(id)

    fun onDestroy() {
        destroy()
    }
}
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


data class HomeState(
    val foods: List<Food> = emptyList()
)

class HomeComponent(
    context: ComponentContext,
    private val repo: FoodRepository,
    private val onFoodClick: (String) -> Unit
) : BaseComponent<HomeState>(context, HomeState()) {

    init {
        scope.launch {
            repo.getAllFoods().collect { list ->
                updateData {
                    copy(foods = list)
                }
            }
        }
    }

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
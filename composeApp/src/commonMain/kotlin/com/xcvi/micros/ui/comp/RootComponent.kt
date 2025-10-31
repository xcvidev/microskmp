package com.xcvi.micros.ui.comp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value

class RootComponent(
    val componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<RootConfig>()

    // The stack of active children
    val childStack: Value<ChildStack<RootConfig, Child>> =
        childStack(
            source = navigation,
            serializer = RootConfig.serializer(), // ✅ Serialization-based routing
            initialConfiguration = RootConfig.FoodList,
            handleBackButton = true,
            childFactory = ::createChild
        )

    // Map RootConfig -> actual child component
    private fun createChild(config: RootConfig, context: ComponentContext): Child =
        when (config) {
            is RootConfig.FoodList ->
                Child.FoodList(
                    FoodListComponent(
                        onFoodSelected = { id -> navigation.push(RootConfig.FoodDetails(id)) },
                        componentContext = componentContext
                    )
                )

            is RootConfig.FoodDetails ->
                Child.FoodDetails(
                    FoodDetailsComponent(foodId = config.foodId, componentContext = componentContext)
                )
        }

    // All possible child screens
    sealed class Child {
        data class FoodList(val component: FoodListComponent) : Child()
        data class FoodDetails(val component: FoodDetailsComponent) : Child()
    }
}



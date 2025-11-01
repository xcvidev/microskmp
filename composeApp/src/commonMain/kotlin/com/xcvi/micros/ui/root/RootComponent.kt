package com.xcvi.micros.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.xcvi.micros.data.FoodRepository
import com.xcvi.micros.ui.details.DetailsComponent
import com.xcvi.micros.ui.home.HomeComponent
import kotlinx.serialization.Serializable
import org.koin.core.Koin


class RootComponent(
    context: ComponentContext,
    private val koin: Koin
) : ComponentContext by context {

    private val navigation = StackNavigation<RootConfig>()


    val stack = childStack(
        source = navigation,
        initialConfiguration = RootConfig.Home,
        serializer = RootConfig.serializer(),
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(config: RootConfig, context: ComponentContext): Any =
        when (config) {
            RootConfig.Home -> HomeComponent(
                context = context,
                repo = koin.get(),
                onFoodClick = {
                    id -> navigation.push(RootConfig.Details(id))
                }
            )
            is RootConfig.Details -> DetailsComponent(
                context = context,
                repo = koin.get(),
                foodId = config.id,
                onBack = { navigation.pop() }
            )
        }

    @Serializable
    sealed class RootConfig {
        @Serializable
        data object Home : RootConfig()

        @Serializable
        data class Details(val id: String) : RootConfig()
    }
}
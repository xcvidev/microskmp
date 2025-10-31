package com.xcvi.micros.ui.comp

import kotlinx.serialization.Serializable

@Serializable
sealed class RootConfig {
    @Serializable
    data object FoodList : RootConfig()

    @Serializable
    data class FoodDetails(val foodId: String) : RootConfig()
}
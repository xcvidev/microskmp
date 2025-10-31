package com.xcvi.micros.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.xcvi.micros.ui.comp.RootComponent

@Composable
fun RootContent(component: RootComponent) {
    Children(
        stack = component.childStack,
        animation = stackAnimation(fade())
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.FoodList -> FoodListScreen(child.component)
            is RootComponent.Child.FoodDetails -> FoodDetailsScreen(child.component)
        }
    }
}
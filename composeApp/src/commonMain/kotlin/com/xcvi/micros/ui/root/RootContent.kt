package com.xcvi.micros.ui.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.xcvi.micros.ui.details.DetailsComponent
import com.xcvi.micros.ui.details.DetailsScreen
import com.xcvi.micros.ui.home.HomeComponent
import com.xcvi.micros.ui.home.HomeScreen

@Composable
fun RootContent(root: RootComponent) {
    Children(stack = root.stack) { child ->
        when (val instance = child.instance) {
            is HomeComponent -> HomeScreen(instance)
            is DetailsComponent -> DetailsScreen(instance)
        }
    }
}
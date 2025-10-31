package com.xcvi.micros

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.xcvi.micros.ui.RootContent
import com.xcvi.micros.ui.comp.RootComponent
import org.jetbrains.compose.ui.tooling.preview.Preview

fun createRootComponent(): RootComponent {
    val lifecycle = LifecycleRegistry()
    val context = DefaultComponentContext(lifecycle)
    return RootComponent(context)
}

@Composable
@Preview
fun App() {
    val root = createRootComponent()
    MaterialTheme {
        RootContent(root)
    }
}
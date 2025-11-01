package com.xcvi.micros

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.xcvi.micros.di.commonModule
import com.xcvi.micros.di.initKoin
import com.xcvi.micros.ui.root.RootComponent
import com.xcvi.micros.ui.root.RootContent
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import kotlin.getValue


fun createRootComponent(): RootComponent {
    val lifecycle = LifecycleRegistry()
    val context = DefaultComponentContext(lifecycle)
    val koin = initKoin()
    return RootComponent(context, koin)
}

@Composable
fun App() {
    val root = remember { createRootComponent() }
    MaterialTheme {
        RootContent(root)
    }
}



















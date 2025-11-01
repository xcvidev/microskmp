package com.xcvi.micros

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.xcvi.micros.ui.root.RootComponent
import com.xcvi.micros.ui.root.RootContent


@Composable
fun App(root: RootComponent) {
    MaterialTheme {
        RootContent(root)
    }
}


















package com.xcvi.micros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.xcvi.micros.di.createRootComponent
import com.xcvi.micros.di.initKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val driverFactory = AndroidDatabaseDriverFactory(this)
        val koin = initKoin(driverFactory)
        val root = createRootComponent(koin)

        setContent {
            App(root) // pass root from platform
        }
    }
}

package com.xcvi.micros

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.xcvi.micros.license.LicensingService
import com.xcvi.micros.ui.LicensingGate
import com.xcvi.micros.ui.root.RootComponent
import com.xcvi.micros.ui.root.RootContent


@Composable
fun App(root: RootComponent, licensingService: LicensingService) {
    MaterialTheme {
        LicensingGate(licensingService) {
            // Only shown if licensed
            RootContent(root)
        }
    }
}

















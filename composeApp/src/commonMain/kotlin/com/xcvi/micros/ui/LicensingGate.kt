package com.xcvi.micros.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import com.xcvi.micros.license.LicensingService

@Composable
fun LicensingGate(licensingService: LicensingService, content: @Composable () -> Unit) {
    val isLicensed by produceState(initialValue = false) {
        value = licensingService.isLicensed()
    }

    if (isLicensed) {
        content()
    } else {
        Text("App not licensed. Please download from the App Store.")
    }
}
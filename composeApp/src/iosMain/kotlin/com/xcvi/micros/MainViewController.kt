package com.xcvi.micros

import androidx.compose.ui.window.ComposeUIViewController
import com.xcvi.micros.di.createRootComponent
import com.xcvi.micros.di.initKoin

fun MainViewController() = ComposeUIViewController {
    val driverFactory = IOSDatabaseDriverFactory("micros.db")
    val koin = initKoin(driverFactory)
    val root = createRootComponent(koin)

    val licensingService = IOSLicensingService()

    App(root, licensingService)

}

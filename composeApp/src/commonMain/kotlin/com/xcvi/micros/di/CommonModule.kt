package com.xcvi.micros.di

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.xcvi.micros.MicrosDB
import com.xcvi.micros.data.DatabaseDriverFactory
import com.xcvi.micros.data.FoodRepositoryImplementation
import com.xcvi.micros.data.FoodRepository
import com.xcvi.micros.ui.root.RootComponent
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun createRootComponent(koin: Koin): RootComponent {
    val lifecycle = LifecycleRegistry()
    val context = DefaultComponentContext(lifecycle)
    return RootComponent(context, koin)
}

private var koinInstance: Koin? = null

fun initKoin(databaseDriverFactory: DatabaseDriverFactory): Koin {
    if (koinInstance == null) {
        koinInstance = startKoin {
            modules(
                module {
                    single { databaseDriverFactory }
                    single { MicrosDB(get<DatabaseDriverFactory>().createDriver()) }
                    single<FoodRepository> { FoodRepositoryImplementation(get()) }
                }
            )
        }.koin
    }
    return koinInstance!!
}

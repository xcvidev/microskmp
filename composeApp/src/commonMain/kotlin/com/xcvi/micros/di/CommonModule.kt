package com.xcvi.micros.di

import com.xcvi.micros.data.FakeFoodRepository
import com.xcvi.micros.data.FoodRepository
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.dsl.module

val commonModule = module {
    single<FoodRepository> { FakeFoodRepository() }
}

private var koinInstance: Koin? = null

fun initKoin(): Koin {
    if (koinInstance == null) {
        koinInstance = startKoin {
            modules(commonModule)
        }.koin
    }
    return koinInstance!!
}
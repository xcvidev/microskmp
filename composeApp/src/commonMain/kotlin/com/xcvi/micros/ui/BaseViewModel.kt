package com.xcvi.micros.ui

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel


open class BaseViewModel(
    componentContext: ComponentContext
) : ComponentContext by componentContext {
    protected val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    open fun onDestroy() = viewModelScope.cancel()
}
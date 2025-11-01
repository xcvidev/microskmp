package com.xcvi.micros.ui.root

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

open class BaseComponent(protected val context: ComponentContext) : ComponentContext by context {
    // Manually create a CoroutineScope
    protected val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun destroy() {
        scope.cancel()
    }
}
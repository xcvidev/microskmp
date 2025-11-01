package com.xcvi.micros.ui.root

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

open class BaseComponent<T>(protected val context: ComponentContext, initial: T) : ComponentContext by context {

    protected val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    var state by mutableStateOf(initial)
        protected set
    protected fun updateData(update: T.() -> T) {
        state = state.update()
    }

    fun destroy() {
        scope.cancel()
    }
}

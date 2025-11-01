package com.xcvi.micros.ui.home

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(component: HomeComponent) {
    Column {
        component.foods.forEach { food ->
            Text(
                text = food.name,
                modifier = androidx.compose.ui.Modifier.clickable {
                    component.onFoodSelected(food.barcode)
                }
            )
        }
    }
}
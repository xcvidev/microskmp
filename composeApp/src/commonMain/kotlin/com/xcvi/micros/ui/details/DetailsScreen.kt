package com.xcvi.micros.ui.details
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(component: DetailsComponent) {
    val food = component.food
    Column {
        Text("Food details:")
        Text(food?.name ?: "Not found")
        Button(onClick = component::onBackClick) {
            Text("Back")
        }
    }
}
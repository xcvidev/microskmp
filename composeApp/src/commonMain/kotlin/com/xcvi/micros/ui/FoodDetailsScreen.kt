package com.xcvi.micros.ui
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.xcvi.micros.ui.comp.FoodDetailsComponent

@Composable
fun FoodDetailsScreen(component: FoodDetailsComponent) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Food Details", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        Text(component.food.value, style = MaterialTheme.typography.bodyLarge)
    }
}
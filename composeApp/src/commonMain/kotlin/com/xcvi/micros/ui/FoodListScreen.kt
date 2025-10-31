package com.xcvi.micros.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.xcvi.micros.ui.comp.FoodListComponent

@Composable
fun FoodListScreen(component: FoodListComponent) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Foods", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        component.foods.forEach { food ->
            Text(
                text = food,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { component.onClick(food) }
                    .padding(vertical = 8.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
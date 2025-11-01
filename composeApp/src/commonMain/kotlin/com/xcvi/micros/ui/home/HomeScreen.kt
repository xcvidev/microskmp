package com.xcvi.micros.ui.home

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(component: HomeComponent) {
    Column {
        Button(
            modifier = Modifier.padding(24.dp).fillMaxWidth(),
            onClick = component::insert
        ){
            Text("Insert Random")
        }

        component.state.foods.forEach { food ->
            Text(
                text = food.name,
                modifier = Modifier.clickable {
                    component.onFoodSelected(food.barcode)
                }
            )
        }
    }
}
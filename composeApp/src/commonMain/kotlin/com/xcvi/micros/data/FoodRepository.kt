package com.xcvi.micros.data

import com.xcvi.micros.Food

interface FoodRepository {
    fun getAllFoods(): List<Food>
    fun getFoodById(id: String): Food?
}

class FakeFoodRepository : FoodRepository {
    private val foods = listOf(
        emptyFood().copy("1", "Apple"),
        emptyFood().copy("2", "Banana"),
        emptyFood().copy("3", "Carrot"),
        emptyFood().copy("4", "Donut"),
        emptyFood().copy("5", "Egg"),
    )

    override fun getAllFoods() = foods
    override fun getFoodById(id: String) = foods.find { it.barcode == id }
}



fun emptyFood(): Food {
    return Food(
        barcode = "",
        name = "",
        isFavorite = 0,
        isRecent = 0,
        isAI = 0,
        tag = "",
        tagwordcount = 0,
        calories = 0.0,
        protein = 0.0,
        carbohydrates = 0.0,
        fats = 0.0,
        saturatedFats = 0.0,
        fiber = 0.0,
        sugars = 0.0,
        calcium = 0.0,
        iron = 0.0,
        magnesium = 0.0,
        potassium = 0.0,
        sodium = 0.0,
        zinc = 0.0,
        fluoride = 0.0,
        iodine = 0.0,
        phosphorus = 0.0,
        manganese = 0.0,
        selenium = 0.0,
        vitaminA = 0.0,
        vitaminB1 = 0.0,
        vitaminB2 = 0.0,
        vitaminB3 = 0.0,
        vitaminB4 = 0.0,
        vitaminB5 = 0.0,
        vitaminB6 = 0.0,
        vitaminB9 = 0.0,
        vitaminB12 = 0.0,
        vitaminC = 0.0,
        vitaminD = 0.0,
        vitaminE = 0.0,
        vitaminK = 0.0,
        histidine = 0.0,
        isoleucine = 0.0,
        leucine = 0.0,
        lysine = 0.0,
        methionine = 0.0,
        phenylalanine = 0.0,
        threonine = 0.0,
        tryptophan = 0.0,
        valine = 0.0
    )
}
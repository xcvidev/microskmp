package com.xcvi.micros.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.xcvi.micros.Food
import com.xcvi.micros.MicrosDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

interface FoodRepository {
    fun getAllFoods(): Flow<List<Food>>
    fun getFoodById(id: String): Food?
    suspend fun insertRandomFood()

}

class FoodRepositoryImplementation(
    val db: MicrosDB
) : FoodRepository {


    override suspend fun insertRandomFood() {
        withContext(Dispatchers.IO) {
            val names = listOf("Banana", "Apple", "Orange", "Strawberry")
            val barcodes = listOf("1", "2", "3", "4", "5")
            val food = emptyFood().copy(
                barcode = barcodes.random(),
                name = names.random()
            )
            insertFood(db, food)
        }
    }

    override fun getAllFoods(): Flow<List<Food>> = db.foodQueries.getAll().asFlow().mapToList(
        context = Dispatchers.IO
    )

    override fun getFoodById(id: String) = db.foodQueries.getFoodByBarcode(id).executeAsOneOrNull()
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


fun insertFood(db: MicrosDB, food: Food) {
    db.foodQueries.upsert(
        barcode = food.barcode,
        name = food.name,
        isFavorite = food.isFavorite,
        isRecent = food.isRecent,
        isAI = food.isAI,
        tag = food.tag,
        tagwordcount = food.tagwordcount,
        calories = food.calories,
        protein = food.protein,
        carbohydrates = food.carbohydrates,
        fats = food.fats,
        saturatedFats = food.saturatedFats,
        fiber = food.fiber,
        sugars = food.sugars,
        calcium = food.calcium,
        iron = food.iron,
        magnesium = food.magnesium,
        potassium = food.potassium,
        sodium = food.sodium,
        zinc = food.zinc,
        fluoride = food.fluoride,
        iodine = food.iodine,
        phosphorus = food.phosphorus,
        manganese = food.manganese,
        selenium = food.selenium,
        vitaminA = food.vitaminA,
        vitaminB1 = food.vitaminB1,
        vitaminB2 = food.vitaminB2,
        vitaminB3 = food.vitaminB3,
        vitaminB4 = food.vitaminB4,
        vitaminB5 = food.vitaminB5,
        vitaminB6 = food.vitaminB6,
        vitaminB9 = food.vitaminB9,
        vitaminB12 = food.vitaminB12,
        vitaminC = food.vitaminC,
        vitaminD = food.vitaminD,
        vitaminE = food.vitaminE,
        vitaminK = food.vitaminK,
        histidine = food.histidine,
        isoleucine = food.isoleucine,
        leucine = food.leucine,
        lysine = food.lysine,
        methionine = food.methionine,
        phenylalanine = food.phenylalanine,
        threonine = food.threonine,
        tryptophan = food.tryptophan,
        valine = food.valine,
    )
}
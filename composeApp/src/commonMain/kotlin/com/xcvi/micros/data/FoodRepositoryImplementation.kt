package com.xcvi.micros.data

import com.xcvi.micros.Food
import com.xcvi.micros.MicrosDB
import com.xcvi.micros.domain.Response
import com.xcvi.micros.domain.fetchAndCache
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.collections.plus

class FoodRepositoryImplementation(
    val db: MicrosDB,
){

    suspend fun search(query: String): Response<List<Food>> {
        return fetchAndCache(
            apiCall = {
                coroutineScope {
                    val en = async { searchProduct(query = query,  "en") }.await()
                    val it = async { searchProduct(query = query,  "it") }.await()
                    val fr = async { searchProduct(query = query,  "fr") }.await()
                    val es = async { searchProduct(query = query,  "es") }.await()
                    if (en != null && it != null && fr != null && es != null) {
                        (en + fr + it + es).distinctBy { it.barcode }
                    } else {
                        null
                    }
                }
            },
            cacheCall = { apiRes ->
                apiRes.forEach { food ->
                    insertFood(db = db, food = food)
                }
            },
            dbCall = { response ->
                val barcodes = response.map { it.barcode }
                val remote = db.foodQueries.getFoodByBarcodes(barcodes).executeAsList()
                val local = db.foodQueries.searchFoodByName(query).executeAsList()
                (local + remote).distinctBy { it.barcode }
            },
            fallbackRequest =null,
            fallbackDbCall = {db.foodQueries.searchFoodByName(query).executeAsList()},
        )
    }

}


















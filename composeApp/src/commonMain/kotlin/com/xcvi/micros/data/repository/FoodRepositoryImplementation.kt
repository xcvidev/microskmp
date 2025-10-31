package com.xcvi.micros.data.repository

import com.xcvi.micros.Food
import com.xcvi.micros.MicrosDB
import com.xcvi.micros.data.source.remote.FoodApi
import com.xcvi.micros.domain.Failure
import com.xcvi.micros.domain.Response
import com.xcvi.micros.domain.fetchAndCache
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.collections.plus

class FoodRepositoryImplementation(
    val db: MicrosDB,
    val api: FoodApi
){
    suspend fun search(query: String): Response<List<Food>> {
        return fetchAndCache(
            apiCall = {
                coroutineScope {
                    val en = async { api.searchProduct(query = query,  "en") }.await()
                    val it = async { api.searchProduct(query = query,  "it") }.await()
                    val fr = async { api.searchProduct(query = query,  "fr") }.await()
                    val es = async { api.searchProduct(query = query,  "es") }.await()
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


    suspend fun scan(barcode: String): Response<Food> {
        return try {
            val local = db.foodQueries.getFoodByBarcode(barcode).executeAsOneOrNull()
            if (local != null) {
                Response.Success(local)
            } else {
                fetchAndCache(
                    apiCall = { api.scanProduct(barcode)},
                    cacheCall = { response ->
                        insertFood(db = db, food = response)
                    },
                    dbCall = { db.foodQueries.getFoodByBarcode(barcode).executeAsOneOrNull() },
                    fallbackRequest = null,
                    fallbackDbCall = { null }
                )
            }
        } catch (e: Exception) {
            Response.Error(Failure.Network)
        }
    }

    fun getFood(barcode: String): Response<Food> {
        return try {
            val food = db.foodQueries.getFoodByBarcode(barcode).executeAsOneOrNull()
            if (food != null) {
                Response.Success(food)
            } else {
                Response.Error(Failure.EmptyResult)
            }
        } catch (e: Exception) {
            Response.Error(Failure.Database)
        }
    }
}


















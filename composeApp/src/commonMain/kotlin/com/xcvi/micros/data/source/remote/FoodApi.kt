package com.xcvi.micros.data.source.remote

import com.xcvi.micros.Food
import com.xcvi.micros.data.repository.emptyFood
import com.xcvi.micros.data.repository.getDisplayName
import com.xcvi.micros.data.repository.getTag
import com.xcvi.micros.data.source.remote.dto.ScanDTO
import com.xcvi.micros.data.source.remote.dto.SearchDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class FoodApi {

    val productClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = false
                isLenient = true
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 5000
            socketTimeoutMillis = 3000
            connectTimeoutMillis = 3000
        }
    }
    val key = ""
    val aiClient = HttpClient {
        install(HttpTimeout) {
            requestTimeoutMillis = 10000
            socketTimeoutMillis = 8000
            connectTimeoutMillis = 3000
        }

        defaultRequest {
            header("Authorization", "Bearer $key")
            contentType(ContentType.Application.Json)
        }
    }

    suspend fun scanProduct(barcode: String): Food? {
        val url = "https://world.openfoodfacts.org/api/v3/product/$barcode"
        val res = productClient.get(url).body<ScanDTO>().product ?: return null
        val displayName = getDisplayName(name = res.name, brand = res.brands)
        val (tag, count) = getTag(name = res.name, brand = res.brands)
        return emptyFood().copy(
            barcode = res.barcode,
            name = displayName,
            tag = tag,
            tagwordcount = count,
            calories = res.nutriments.kcal,
            fats = res.nutriments.fat_100g,
            saturatedFats = res.nutriments.saturated_fat_100g,
            carbohydrates = res.nutriments.carbohydrates_100g,
            sugars = res.nutriments.sugars_100g,
            fiber = res.nutriments.fiber_100g,
            protein = res.nutriments.proteins_100g,
            sodium = res.nutriments.salt_100g * 0.4,
        )
    }

    suspend fun searchProduct(
        query: String,
        language: String,
        page: Int = 1,
        pageSize: Int = 100
    ): List<Food>? {
        val url = "https://search.openfoodfacts.org/search"

        val res = productClient.get {
            url(url)
            parameter("q", query)
            parameter("langs", language)
            parameter("page", page)
            parameter("page_size", pageSize)
            parameter("fields", "code,product_name,nutriments,brands")
        }
        return res.body<SearchDTO>().hits.map {
            val displayName = getDisplayName(name = it.name, brand = it.brands.joinToString())
            val (tag, count) = getTag(name = it.name, brand = it.brands.joinToString())
            emptyFood().copy(
                barcode = it.barcode,
                name = displayName,
                tag = tag,
                tagwordcount = count,
                calories = it.nutriments.kcal,
                fats = it.nutriments.fat_100g,
                saturatedFats = it.nutriments.saturated_fat_100g,
                carbohydrates = it.nutriments.carbohydrates_100g,
                sugars = it.nutriments.sugars_100g,
                fiber = it.nutriments.fiber_100g,
                protein = it.nutriments.proteins_100g,
                sodium = it.nutriments.salt_100g * 0.4,
            )
        }
    }


}
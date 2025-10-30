package com.xcvi.micros.data

import com.xcvi.micros.Food
import com.xcvi.micros.domain.toAscii


import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

val productClient = HttpClient {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
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

@Serializable
data class SearchDTO(
    val page: Int = 1,
    val page_size: Int = 100,
    val hits: List<SearchProductDTO> = emptyList()
)

@Serializable
data class SearchProductDTO(
    @SerialName("code") val barcode: String = "",
    @SerialName("brands") val brands: List<String> = emptyList(),
    @SerialName("product_name") val name: String = "",
    @SerialName("nutriments") val nutriments: NutrimentsDTO = NutrimentsDTO()
)


@Serializable
data class NutrimentsDTO(
    val carbohydrates_100g: Double = -1.0,
    @SerialName("energy-kcal_100g")
    val kcal: Double = -1.0,
    val fat_100g: Double = -1.0,
    val fiber_100g: Double = -1.0,
    val proteins_100g: Double = -1.0,
    val salt_100g: Double = -1.0,
    @SerialName("saturated-fat_100g")
    val saturated_fat_100g: Double = -1.0,
    val sugars_100g: Double = -1.0
)

fun getDisplayName(name: String, brand: String): String {
    if (name.isBlank() && brand.isBlank()) return ""
    val displayName = if (name.isBlank()) {
        brand.lowercase().replaceFirstChar { it.uppercase() }
    } else {
        if (brand.isBlank()) {
            name.lowercase().replaceFirstChar { it.uppercase() }
        } else {
            "${name.lowercase().replaceFirstChar { it.uppercase() }} (${brand})"
        }
    }

    return displayName
}

fun getTag(name: String, brand: String): Pair<String, Long> {
    val displayName = getDisplayName(name = name, brand = brand)
    val tag = displayName.toAscii()
    val count = tag.split("\\s+".toRegex()).filter { it.isNotEmpty() }.size.toLong()
    return Pair(tag, count)
}
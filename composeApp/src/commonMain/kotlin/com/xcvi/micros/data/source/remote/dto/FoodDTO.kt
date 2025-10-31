package com.xcvi.micros.data.source.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


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
data class ScanDTO(
    val product: ScanProductDTO? = null,
    val code: String = "",
    val status: String = "",
)

@Serializable
data class ScanProductDTO(
    @SerialName("code")
    val barcode: String = "",
    @SerialName("product_name")
    val name: String = "",
    val brands: String = "",
    val nutriments: NutrimentsDTO = NutrimentsDTO(),
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


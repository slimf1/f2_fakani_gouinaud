package fr.isima.expressvoyage.models

import kotlinx.serialization.Serializable

@Serializable
data class CountryLocationApiResponse(
    val error: Boolean,
    val msg: String,
    val data: CountryLocation,
) : java.io.Serializable

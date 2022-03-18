package fr.isima.expressvoyage.models

import kotlinx.serialization.Serializable

@Serializable
data class CountriesListApiResponse(
    val error: Boolean,
    val msg: String,
    val data: List<CountryData>
)

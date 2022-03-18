package fr.isima.expressvoyage.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class CountryCurrencyApiResponse(
    val error: Boolean,
    val msg: String,
    val data: CountryCurrency
) {
    @Entity(tableName = "CountryCurrency")
    data class CountryCurrency (
        val name: String,
        val currency: String,
        @PrimaryKey
        val iso2: String,
        val iso3: String,
    )
}

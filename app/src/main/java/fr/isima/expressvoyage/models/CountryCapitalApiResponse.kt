package fr.isima.expressvoyage.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class CountryCapitalApiResponse(
    val error: Boolean,
    val msg: String,
    val data: CountryCapital
) {
    @Entity(tableName = "CountryCapital")
    data class CountryCapital(
        val name: String,
        val capital: String,
        @PrimaryKey
        val iso2: String,
        val iso3: String
    )
}

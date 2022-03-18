package fr.isima.expressvoyage.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class CountryData(
    @ColumnInfo
    val name: String,
    @PrimaryKey
    val iso2: String,
    @ColumnInfo
    val iso3: String,
    @ColumnInfo
    @SerializedName("flag")
    val flagUrl: String,
    var isFavorite: Boolean = false
) : java.io.Serializable

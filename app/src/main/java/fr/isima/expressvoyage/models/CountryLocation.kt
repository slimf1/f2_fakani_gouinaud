package fr.isima.expressvoyage.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Entity(tableName = "CountryLocation")
@Serializable
data class CountryLocation(
    @ColumnInfo
    var name: String = "",
    @PrimaryKey
    @SerializedName("iso2")
    var codeIso2: String = "",
    @ColumnInfo
    var long: Double = 0.0,
    @ColumnInfo
    var lat: Double = 0.0,
) : java.io.Serializable

package fr.isima.expressvoyage.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.isima.expressvoyage.models.CountryCapitalApiResponse

@Dao
interface CountryCapitalDao {
    @Query("SELECT * FROM CountryCapital WHERE iso2 = :iso2code")
    fun findByIsoCode(iso2code: String): CountryCapitalApiResponse.CountryCapital

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(countryCapital: CountryCapitalApiResponse.CountryCapital)
}

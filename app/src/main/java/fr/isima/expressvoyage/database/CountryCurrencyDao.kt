package fr.isima.expressvoyage.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.isima.expressvoyage.models.CountryCurrencyApiResponse

@Dao
interface CountryCurrencyDao {
    @Query("SELECT * FROM CountryCurrency WHERE iso2 = :iso2code")
    fun findByIsoCode(iso2code: String): CountryCurrencyApiResponse.CountryCurrency

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(countryCurrency: CountryCurrencyApiResponse.CountryCurrency)
}

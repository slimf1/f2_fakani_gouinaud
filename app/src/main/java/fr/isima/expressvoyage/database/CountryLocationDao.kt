package fr.isima.expressvoyage.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.isima.expressvoyage.models.CountryLocation

@Dao
interface CountryLocationDao {
    @Query("SELECT * FROM CountryLocation")
    fun getAll(): List<CountryLocation>

    @Query("SELECT * FROM CountryLocation WHERE codeIso2 = :iso2code")
    fun findByIsoCode(iso2code: String): CountryLocation

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg countryLocation: CountryLocation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(countryLocation: CountryLocation)
}

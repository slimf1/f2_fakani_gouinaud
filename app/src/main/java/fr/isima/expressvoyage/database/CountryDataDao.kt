package fr.isima.expressvoyage.database

import androidx.room.*
import fr.isima.expressvoyage.models.CountryData

@Dao
interface CountryDataDao {
    @Query("SELECT * FROM CountryData")
    fun getAll(): List<CountryData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg countryData: CountryData)
}

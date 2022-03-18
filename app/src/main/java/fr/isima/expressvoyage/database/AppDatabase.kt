package fr.isima.expressvoyage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.isima.expressvoyage.models.*

@Database(
    entities = [
        CountryData::class,
        CountryLocation::class,
        CountryCurrencyApiResponse.CountryCurrency::class,
        CountryCapitalApiResponse.CountryCapital::class
   ],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countryDataDao(): CountryDataDao
    abstract fun countryLocationDao(): CountryLocationDao
    abstract fun countryCapitalDao(): CountryCapitalDao
    abstract fun countryCurrencyDao(): CountryCurrencyDao
}

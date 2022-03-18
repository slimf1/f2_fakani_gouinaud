package fr.isima.expressvoyage.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.isima.expressvoyage.api.HttpClient
import fr.isima.expressvoyage.database.DbConnection
import fr.isima.expressvoyage.models.CountryData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    val countries = MutableLiveData<List<CountryData>>()
    private var allCountries: List<CountryData> = ArrayList()

    fun fetchLocations(appContext: Context) = viewModelScope.launch {
        val db = DbConnection.getDb(appContext)
        try {
            allCountries = HttpClient.getLocations()
            launch(Dispatchers.Default) {
                // Insert the countries into the database
                db?.countryDataDao()?.insertAll(*allCountries.toTypedArray())
            }
        } catch(ex: Exception) {
            // Fetch from the database if we cannot make an API call
            allCountries = withContext(Dispatchers.IO) {
                db?.countryDataDao()?.getAll() ?: listOf()
            }
        }
        countries.value = allCountries
    }
}

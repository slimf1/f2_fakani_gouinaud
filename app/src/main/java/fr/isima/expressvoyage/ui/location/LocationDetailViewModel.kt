package fr.isima.expressvoyage.ui.location

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.isima.expressvoyage.api.HttpClient
import fr.isima.expressvoyage.database.DbConnection
import fr.isima.expressvoyage.models.CountryCapitalApiResponse
import fr.isima.expressvoyage.models.CountryCurrencyApiResponse
import fr.isima.expressvoyage.models.CountryLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocationDetailViewModel : ViewModel() {
    val locationPosition = MutableLiveData<CountryLocation?>()
    val countryCurrency = MutableLiveData<CountryCurrencyApiResponse.CountryCurrency?>()
    val countryCapital = MutableLiveData<CountryCapitalApiResponse.CountryCapital?>()

    fun fetchPosition(appContext: Context, iso2code: String) = viewModelScope.launch {
        val db = DbConnection.getDb(appContext)
        var countryLocation: CountryLocation?

        try {
            // Fetch then insert into the database
            countryLocation = HttpClient.getPosition(iso2code).data
            launch(Dispatchers.Default) {
                db?.countryLocationDao()?.insertOne(countryLocation!!)
            }
        } catch(ex: Exception) {
            // We use the database to retrieve the entity if the network doesn't work
            countryLocation = withContext(Dispatchers.IO) {
                db?.countryLocationDao()?.findByIsoCode(iso2code)
            }
        }
        locationPosition.value = countryLocation
    }

    fun fetchCurrency(appContext: Context, iso2code: String) = viewModelScope.launch {
        val db = DbConnection.getDb(appContext)
        var currency: CountryCurrencyApiResponse.CountryCurrency?
        try {
            currency = HttpClient.getCurrency(iso2code).data
            launch(Dispatchers.Default) {
                db?.countryCurrencyDao()?.insertOne(currency!!)
            }
        } catch(ex: java.lang.Exception) {
            currency = withContext(Dispatchers.IO) {
                db?.countryCurrencyDao()?.findByIsoCode(iso2code)
            }
        }
        countryCurrency.value = currency
    }

    fun fetchCapital(appContext: Context, iso2code: String) = viewModelScope.launch {
        val db = DbConnection.getDb(appContext)
        var capital: CountryCapitalApiResponse.CountryCapital?
        try {
            capital = HttpClient.getCapital(iso2code).data
            launch(Dispatchers.Default) {
                db?.countryCapitalDao()?.insertOne(capital!!)
            }
        } catch(ex: Exception) {
            capital = withContext(Dispatchers.IO) {
                db?.countryCapitalDao()?.findByIsoCode(iso2code)
            }
        }
        countryCapital.value = capital
    }
}

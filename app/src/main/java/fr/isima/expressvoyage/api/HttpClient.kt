package fr.isima.expressvoyage.api

import fr.isima.expressvoyage.models.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.http.*

// Singleton used to do the API calls to the countriesnow API
object HttpClient {

    private val API_BASE_URL = Url("https://countriesnow.space/api/v0.1")

    private val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = GsonSerializer {
                setPrettyPrinting()
                disableHtmlEscaping()
            }
        }
    }

    suspend fun getLocations(): List<CountryData> {
        val listApiResponse: CountriesListApiResponse = client
            .get("$API_BASE_URL/countries/flag/images") {
                contentType(ContentType.Application.Json)
            }
        return listApiResponse.data
    }

    // Most API calls follow the same structure with only slight changes
    // We use this generic method to reduce code duplication
    private suspend inline fun <reified T> getFromCountry(resource: String, iso2code: String): T {
        val apiResponse: T = client
            .post("$API_BASE_URL/countries/$resource") {
                contentType(ContentType.Application.Json)
                body = object {
                    val iso2 = iso2code
                }
            }
        return apiResponse
    }

    suspend fun getPosition(iso2code: String): CountryLocationApiResponse {
        return getFromCountry("positions", iso2code)
    }

    suspend fun getCurrency(iso2code: String): CountryCurrencyApiResponse {
        return getFromCountry("currency", iso2code)
    }

    suspend fun getCapital(iso2code: String): CountryCapitalApiResponse {
        return getFromCountry("capital", iso2code)
    }
}

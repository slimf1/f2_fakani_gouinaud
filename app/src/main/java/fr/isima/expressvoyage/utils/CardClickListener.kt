package fr.isima.expressvoyage.utils

import fr.isima.expressvoyage.models.CountryData

interface CardClickListener {
    fun onCardClick(countryData: CountryData)
}

package org.isen.news.ctrl

import org.isen.news.model.data.Country
import org.isen.news.model.impl.CountryModel

class CountryController(
        countryModel: CountryModel
) : NewsDefaultController() {
    init {
        super.models.add(countryModel)
    }

    fun findListOfCountry() : Array<Country> {
        (super.models[0] as CountryModel).findCountries()
        return (super.models[0] as CountryModel).countries
    }
}
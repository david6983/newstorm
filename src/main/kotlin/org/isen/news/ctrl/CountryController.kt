package org.isen.news.ctrl

import org.isen.news.model.impl.CountryModel

class CountryController(
        countryModel: CountryModel
) : NewsDefaultController() {
    init {
        super.models.add(countryModel)
    }

    fun findCountries() {
        (super.models[0] as CountryModel).findCountries()
    }
}
package org.isen.news.ctrl.jfx

import org.isen.news.model.impl.CountryModel

class CountryFxController : NewsDefaultFxController() {
    init {
        super.models.add(CountryModel())
    }

    fun findCountries() {
        (super.models[0] as CountryModel).findCountries()
    }
}
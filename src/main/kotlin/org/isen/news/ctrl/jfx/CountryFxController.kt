package org.isen.news.ctrl.jfx

import org.isen.news.ctrl.INewsControllerParams
import org.isen.news.model.impl.CountryModel

class CountryFxController : NewsDefaultFxController(), INewsControllerParams {
    init {
        super.models.add(CountryModel())
    }

    fun findCountries() {
        (super.models[0] as CountryModel).findCountries()
    }

    override fun changeApiKey(key: String) {
        (super.models.first() as CountryModel).apiKey = key
    }

    override fun changePageSize(pageSize: Int) {
        (super.models.first() as CountryModel).pageSize = pageSize
    }

    override fun findErrorCode(): Int {
        return (super.models.first() as CountryModel).errorCode
    }
}
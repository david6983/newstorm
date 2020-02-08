package org.isen.news.ctrl.jfx

import org.apache.logging.log4j.kotlin.Logging
import org.isen.news.ctrl.INewsControllerParams
import org.isen.news.model.impl.CountryModel
import org.isen.news.model.impl.SourceModel
import org.isen.news.model.impl.TopHeadlineModel
import tornadofx.*

class TopHeadlineFxController : NewsDefaultFxController(), INewsControllerParams {
    companion object : Logging

    init {
        super.models.add(TopHeadlineModel())
        super.models.add(CountryModel())
    }

    fun findBreakingNews(country: String?, category: String?, page: Int?, q: String?) {
        (super.models[0] as TopHeadlineModel).findBreakingNews(country, category, page, q)
    }

    fun findBreakingNewsBySource(source: String, page: Int?, q: String?) {
        (super.models[0] as TopHeadlineModel).findBreakingNewsBySource(source, page, q)
    }

    fun findCountries() {
        (super.models[1] as CountryModel).findCountries()
    }

    fun indexOfAllowedCountryFromAlpha2code(alpha2code: String): Int? {
        return (super.models[1] as CountryModel).indexOfAllowedCountryFromAlpha2code(alpha2code)
    }

    override fun changeApiKey(key: String) {
        (super.models.first() as TopHeadlineModel).apiKey = key
    }

    override fun changePageSize(pageSize: Int) {
        (super.models.first() as TopHeadlineModel).pageSize = pageSize
    }

    override fun findErrorCode(): Int {
        return (super.models.first() as TopHeadlineModel).errorCode
    }
}
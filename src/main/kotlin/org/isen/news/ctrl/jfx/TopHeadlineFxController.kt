package org.isen.news.ctrl.jfx

import org.isen.news.model.impl.CountryModel
import org.isen.news.model.impl.SourceModel
import org.isen.news.model.impl.TopHeadlineModel
import tornadofx.*

class TopHeadlineFxController : NewsDefaultFxController() {
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
}
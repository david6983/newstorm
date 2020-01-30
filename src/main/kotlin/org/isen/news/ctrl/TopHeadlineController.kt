package org.isen.news.ctrl

import org.isen.news.model.impl.CountryModel
import org.isen.news.model.impl.SourceModel
import org.isen.news.model.impl.TopHeadlineModel

class TopHeadlineController(
        topHeadlineModel: TopHeadlineModel,
        countryModel: CountryModel,
        sourceModel: SourceModel
) : NewsDefaultController() {
    init {
        super.models.add(topHeadlineModel)
        super.models.add(countryModel)
        super.models.add(sourceModel)
    }

    fun findBreakingNews(country: String?, category: String?, page: Int?, q: String?) {
        (super.models[0] as TopHeadlineModel).findBreakingNews(country, category, page, q)
    }

    fun findBreakingNewsBySource(source: String, page: Int?, q: String?) {
        (super.models[0] as TopHeadlineModel).findBreakingNewsBySource(source, page, q)
    }

}